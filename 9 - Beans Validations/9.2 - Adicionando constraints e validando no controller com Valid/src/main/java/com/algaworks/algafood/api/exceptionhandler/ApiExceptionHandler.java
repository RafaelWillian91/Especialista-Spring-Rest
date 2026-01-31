package com.algaworks.algafood.api.exceptionhandler;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.PropertyBindingException;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice                        //um tratador global de exceções
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    public static final String MSG_ERRO_GENERICA_USUARIO_FINAL = "Ocorreu um erro interno inesperado no sistema. Tente novamente e se o problema" +
            " persistir, entre em contato com o administrador do sistema.";

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        ProblemType problemType = ProblemType.DADOS_INVALIDOS;
        String detail = "Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente";

        BindingResult bindingResult = ex.getBindingResult();
        List<Problem.Fields> problemFilds = bindingResult.getFieldErrors().stream().map(fieldrror ->
                Problem.Fields.builder()
                        .name(fieldrror.getField())
                        .userMessage(fieldrror.getDefaultMessage())
                        .build())
                .collect(Collectors.toList());

        Problem problem = createProblemBuilder(status, problemType, detail)
                .userMessage(detail)
                .listFilds(problemFilds)
                .build();

        return handleExceptionInternal(ex, problem, headers, status, request);
    }

    /**
     * Trata erros de incompatibilidade de tipo durante o binding de parâmetros da requisição.
     * Exemplo comum: quando um parâmetro esperado como Long recebe um valor String (ex: /recursos/abc).
     * Quando o erro for do tipo MethodArgumentTypeMismatchException, delega para um método específico
     * para gerar uma mensagem de erro mais detalhada e amigável ao consumidor da API.
     * Outros casos de TypeMismatchException são delegados para a implementação padrão do Spring.
     */
    @Override
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers,
                                                        HttpStatus status, WebRequest request) {

        //TypeMismatchException é genérica
        //MethodArgumentTypeMismatchException é mais específica
        if (ex instanceof MethodArgumentTypeMismatchException) {
            return handleMethodArgumentTypeMismatch(
                    (MethodArgumentTypeMismatchException) ex, headers, status, request);
        }

        return super.handleTypeMismatch(ex, headers, status, request);
    }

    private ResponseEntity<Object> handleMethodArgumentTypeMismatch(
            MethodArgumentTypeMismatchException ex, HttpHeaders headers,
            HttpStatus status, WebRequest request) {

        ProblemType problemType = ProblemType.PARAMETRO_INVALIDO;

        String detail = String.format("O parâmetro de URL '%s' recebeu o valor '%s', "
                        + "que é de um tipo inválido. Corrija e informe um valor compatível com o tipo %s.",
                ex.getName(), ex.getValue(), ex.getRequiredType().getSimpleName());

        Problem problem = createProblemBuilder(status, problemType, detail).userMessage(MSG_ERRO_GENERICA_USUARIO_FINAL).build();

        return handleExceptionInternal(ex, problem, headers, status, request);
    }



    /**
     * Trata requisições para URLs que não possuem nenhum handler mapeado.
     * Esse erro ocorre quando o cliente tenta acessar um recurso inexistente,
     * antes mesmo de qualquer método de controller ser executado.
     * O objetivo é garantir que erros 404 retornem um payload padronizado
     * no formato Problem, em vez da resposta genérica do Spring.
     / Esse método só funciona se você tiver algo como:
      spring.mvc.throw-exception-if-no-handler-found=true
      spring.web.resources.add-mappings=false
     */

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        ProblemType problemType = ProblemType.RECURSO_NAO_ENCONTRADO;
        String detail = String.format("O recurso %s, que você tentou acessar, é inexistente", ex.getRequestURL());
        Problem problem = createProblemBuilder(status, problemType, detail).userMessage(MSG_ERRO_GENERICA_USUARIO_FINAL).build();

        return handleExceptionInternal(ex, problem, headers, status, request);
    }

    /**
     * Trata erros de leitura e conversão do corpo da requisição (JSON → objeto Java).
     * Esse método é acionado quando o Spring não consegue desserializar o corpo da requisição,
     * geralmente devido a erros de sintaxe, tipos incompatíveis ou propriedades inexistentes.
     * A exceção HttpMessageNotReadableException funciona como um wrapper; por isso,
     * é necessário inspecionar a causa raiz para identificar o erro real lançado pelo Jackson
     * e gerar uma resposta mais específica e amigável ao consumidor da API.
     */

    @Override
    public ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        //// Obtém a causa raiz da exceção para identificar o erro real de desserialização. biblioteca externa add no pom
        Throwable rootCause = ExceptionUtils.getRootCause(ex);

        //Em tempo de execução, esse objeto é um InvalidFormatException?
        if(rootCause instanceof InvalidFormatException){

            return handleInvalidFormatException((InvalidFormatException) rootCause, headers, status, request);

        }else if(rootCause instanceof PropertyBindingException){
            return handlePropertyBindingException((PropertyBindingException) rootCause, headers, status, request);

        }

        ProblemType problemType = ProblemType.MENSAGEM_INCOMPREENSIVEL;
        String detail = "O corpo da requisição esta inválido. Verifique o erro de sintaxe.";
        Problem problem = createProblemBuilder(status, problemType, detail)
                .userMessage(MSG_ERRO_GENERICA_USUARIO_FINAL)
                .build();
        return this.handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
    }

    /**
     * Trata erros de desserialização quando um atributo do JSON recebe
     * um valor incompatível com o tipo esperado no modelo Java.
     * Exemplo: campo numérico recebendo String ou enum com valor inválido.
     * A InvalidFormatException é lançada pelo Jackson e contém informações
     * sobre o caminho do atributo, o valor recebido e o tipo esperado,
     * permitindo gerar uma mensagem de erro clara e orientativa.
     */

    private ResponseEntity<Object> handleInvalidFormatException(InvalidFormatException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
         {

             String path = joinPath(ex.getPath());

                ProblemType problemType = ProblemType.MENSAGEM_INCOMPREENSIVEL;
                String detail = String.format("a propiedade '%s' recebeu o valor '%s' que é de um tipo" +
                        " inválido.Corrija e informe um valor compatível com o tipo %s", path, ex.getValue(), ex.getTargetType().getSimpleName());
                Problem problem = createProblemBuilder(status, problemType, detail).userMessage(MSG_ERRO_GENERICA_USUARIO_FINAL).build();

                return handleExceptionInternal(ex, problem, headers, status, request);
         }
    }

    /**
     * Trata erros de desserialização quando o JSON contém propriedades
     * que não existem no modelo de destino.
     * Esse erro ocorre quando o cliente envia campos inválidos ou desatualizados,
     * mesmo com o JSON sintaticamente correto.
     * O objetivo é informar claramente qual propriedade é inválida,
     * ajudando o consumidor da API a corrigir a requisição.
     */
    private ResponseEntity<Object> handlePropertyBindingException(PropertyBindingException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {

        // Criei o método joinPath para reaproveitar em todos os métodos que precisam
        // concatenar os nomes das propriedades (separando por ".")
        String path = joinPath(ex.getPath());

        ProblemType problemType = ProblemType.MENSAGEM_INCOMPREENSIVEL;
        String detail = String.format("A propriedade '%s' não existe. "
                + "Corrija ou remova essa propriedade e tente novamente.", path);

        Problem problem = createProblemBuilder(status, problemType, detail).userMessage(MSG_ERRO_GENERICA_USUARIO_FINAL).build();

        return handleExceptionInternal(ex, problem, headers, status, request);
    }

    /**
     * Trata exceções de regra de negócio lançadas pela camada de serviço.
     * Esse tipo de exceção indica que a requisição é válida, mas não pode ser
     * processada devido a uma violação das regras do domínio da aplicação.
     * Retorna HTTP 400 (Bad Request) com mensagem clara para o consumidor da API.
     */
    @ExceptionHandler(NegocioException.class)
    public ResponseEntity<?> handleNegocioException(WebRequest request, NegocioException ex) {

        HttpStatus status = HttpStatus.BAD_REQUEST;
        ProblemType problemType = ProblemType.ERRO_NEGOCIO;
        String detail = ex.getMessage();

        Problem problem = createProblemBuilder(status, problemType, detail).userMessage(detail).build();

        return this.handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);

    }

    /**
     * Trata exceções lançadas quando um recurso solicitado não é encontrado.
     * Esse tipo de exceção indica que a requisição é válida, mas o recurso
     * identificado não existe no sistema.
     * Retorna HTTP 404 (Not Found) com mensagem clara para o consumidor da API.
     */
    @ExceptionHandler(EntidadeNaoEncontradaException.class)
    public ResponseEntity<?> handleEntidadeNaoEncontrada(EntidadeNaoEncontradaException ex,
                                                         WebRequest request) {

        HttpStatus status = HttpStatus.NOT_FOUND;
        ProblemType problemType = ProblemType.RECURSO_NAO_ENCONTRADO;
        String detail = ex.getMessage();

        Problem problem = createProblemBuilder(status, problemType, detail).userMessage(detail).build();

        return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
    }

    /**
     * Trata exceções lançadas quando uma entidade não pode ser removida ou alterada
     * por estar em uso no sistema.
     * Esse tipo de erro indica um conflito de estado, onde a requisição é válida
     * e a entidade existe, mas a operação não pode ser realizada no momento.
     * Retorna HTTP 409 (Conflict).
     */
    @ExceptionHandler(EntidadeEmUsoException.class)
    public ResponseEntity<?> handleCozinhaEmUsoException(WebRequest request, EntidadeEmUsoException ex) {

        HttpStatus status = HttpStatus.CONFLICT;
        ProblemType problemType = ProblemType.ENTIDADE_EM_USO;
        String detail = ex.getMessage();

        Problem problem = createProblemBuilder(status, problemType, detail).userMessage(detail).build();

        return this.handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);

    }

    /**
     * Sobrescreve o tratamento padrão do Spring para garantir que todas as respostas
     * de erro retornadas pela API sigam o formato padronizado Problem.
     * Esse método é acionado internamente pelo Spring em diversos cenários,
     * como erros de validação, binding e exceções lançadas pelo próprio framework.
     * O objetivo é evitar respostas inconsistentes (String ou corpo nulo),
     * garantindo um payload mínimo e previsível para o consumidor da API.
     */

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders
            headers, HttpStatus status, WebRequest request) {

        if (body == null) {

            body = Problem.builder()
                    .timestamp(LocalDateTime.now())
                    .title(status.getReasonPhrase())
                    .status(status.value())
                    .userMessage(MSG_ERRO_GENERICA_USUARIO_FINAL)
                    .build();
        } else if (body instanceof String) {
            body = Problem.builder()
                    .timestamp(LocalDateTime.now())
                    .title((String) body)
                    .status(status.value())
                    .userMessage(MSG_ERRO_GENERICA_USUARIO_FINAL).build();
        }
        return super.handleExceptionInternal(ex, body, headers, status, request);

    }

    /**
     * Handler genérico para exceções não tratadas pela aplicação.
     * Atua como último nível de proteção da API, capturando erros inesperados
     * e garantindo que o cliente receba uma resposta padronizada, segura
     * e sem exposição de detalhes internos da aplicação.
     * Retorna sempre HTTP 500 (Internal Server Error).
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleErrodeSistema (Exception ex, WebRequest request) {

        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        ProblemType problemType = ProblemType.ERRO_DE_SISTEMA;
        String detail = MSG_ERRO_GENERICA_USUARIO_FINAL;

        Problem problem = createProblemBuilder(status, problemType, detail).userMessage(detail).build();

        return this.handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);

    }


    //Open/Closed Principle.
    //O método é fechado à modificação porque não preciso alterá-lo quando surgem novas variações;
    //ele é aberto à extensão porque posso enriquecer o resultado fora dele.
    /**
     * Cria um builder padronizado para respostas de erro no formato Problem.
     * Centraliza a definição dos campos comuns a todos os erros da API,
     * garantindo consistência, reutilização e facilidade de manutenção.
     * Cada handler é responsável apenas por informar o status, o tipo do erro
     * e o detalhe específico do problema ocorrido.
     */

    public Problem.ProblemBuilder createProblemBuilder(HttpStatus status, ProblemType problemType, String detail){
        return Problem.builder()
                .status(status.value())
                .timestamp(LocalDateTime.now())
                .type(problemType.getUri())
                .title(problemType.getTitle())
                .detail(detail);
    }

    /**
     * Converte o caminho do erro fornecido pelo Jackson em uma representação
     * textual legível, concatenando os nomes das propriedades com ".".
     * Exemplo: endereco.cidade.nome
     * Esse método é utilizado para gerar mensagens de erro mais claras
     * quando ocorre falha de desserialização do JSON.
     */
    private String joinPath (List <JsonMappingException.Reference> references) {

        return references.stream()
                .map(reference -> reference.getFieldName())
                .collect(Collectors.joining("."));
    }

}