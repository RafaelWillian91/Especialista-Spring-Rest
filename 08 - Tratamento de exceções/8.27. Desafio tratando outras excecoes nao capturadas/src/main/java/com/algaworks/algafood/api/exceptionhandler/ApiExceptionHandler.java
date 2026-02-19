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
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

//no caso de erro de URL (fluxo do /error), o caminho vem por atributos do request.
@ControllerAdvice                        //um tratador global de exceções
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    // 1. MethodArgumentTypeMismatchException é um subtipo de TypeMismatchException
    // 2. ResponseEntityExceptionHandler já trata TypeMismatchException de forma mais abrangente
    // 3. Então, especializamos o método handleTypeMismatch e verificamos se a exception
    //    é uma instância de MethodArgumentTypeMismatchException
    // 4. Se for, chamamos um método especialista em tratar esse tipo de exception
    // 5. Poderíamos fazer tudo dentro de handleTypeMismatch, mas preferi separar em outro método

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers,
                                                        HttpStatus status, WebRequest request) {

        if (ex instanceof MethodArgumentTypeMismatchException) {
            return handleMethodArgumentTypeMismatch(
                    (MethodArgumentTypeMismatchException) ex, headers, status, request);
        }

        return super.handleTypeMismatch(ex, headers, status, request);
    }
    //6. Todo o erro de URL cai em handleMethodArgumentTypeMismatch
    private ResponseEntity<Object> handleMethodArgumentTypeMismatch(
            MethodArgumentTypeMismatchException ex, HttpHeaders headers,
            HttpStatus status, WebRequest request) {

        ProblemType problemType = ProblemType.PARAMETRO_INVALIDO;

        String detail = String.format("O parâmetro de URL '%s' recebeu o valor '%s', "
                        + "que é de um tipo inválido. Corrija e informe um valor compatível com o tipo %s.",
                ex.getName(), ex.getValue(), ex.getRequiredType().getSimpleName());

        Problem problem = createProblemBuilder(status, problemType, detail).build();

        return handleExceptionInternal(ex, problem, headers, status, request);
    }


    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        ProblemType problemType = ProblemType.RECURSO_NAO_ENCONTRADO;

        String detail = String.format("O recurso %s, que você tentou acessar, é inexistente", ex.getRequestURL());

        Problem problem = createProblemBuilder(status, problemType, detail).build();

        return handleExceptionInternal(ex, problem, headers, status, request);
    }

    @Override
    public ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        Throwable rootCause = ExceptionUtils.getRootCause(ex);

        //Em tempo de execução, esse objeto é um InvalidFormatException?
        if(rootCause instanceof InvalidFormatException){

            return handleInvalidFormatException((InvalidFormatException) rootCause, headers, status, request);

        }else if(rootCause instanceof PropertyBindingException){
            return handlePropertyBindingException((PropertyBindingException) rootCause, headers, status, request);

        } else if (rootCause instanceof MethodArgumentTypeMismatchException) {
            return handleMethodArgumentTypeMismatchException ((MethodArgumentTypeMismatchException) rootCause, headers, status, request);

        }

        ProblemType problemType = ProblemType.MENSAGEM_INCOMPREENSIVEL;
        String detail = "O corpo da requisição esta inválido. Verifique o erro de sintaxe.";
        Problem problem = createProblemBuilder(status, problemType, detail).build();
        return this.handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
    }

    private ResponseEntity<Object> handleMethodArgumentTypeMismatchException (MethodArgumentTypeMismatchException e, HttpHeaders headers, HttpStatus status, WebRequest request){

        return ResponseEntity.ok("Parametro invalido");

    }
    private ResponseEntity<Object> handleInvalidFormatException(InvalidFormatException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
         {

             String path = joinPath(ex.getPath());

                ProblemType problemType = ProblemType.MENSAGEM_INCOMPREENSIVEL;
                String detail = String.format("a propiedade '%s' recebeu o valor '%s' que é de um tipo" +
                        " inválido.Corrija e informe um valor compatível com o tipo %s", path, ex.getValue(), ex.getTargetType().getSimpleName());
                Problem problem = createProblemBuilder(status, problemType, detail).build();
                return handleExceptionInternal(ex, problem, headers, status, request);
         }
    }


    private ResponseEntity<Object> handlePropertyBindingException(PropertyBindingException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {

        // Criei o método joinPath para reaproveitar em todos os métodos que precisam
        // concatenar os nomes das propriedades (separando por ".")
        String path = joinPath(ex.getPath());

        ProblemType problemType = ProblemType.MENSAGEM_INCOMPREENSIVEL;
        String detail = String.format("A propriedade '%s' não existe. "
                + "Corrija ou remova essa propriedade e tente novamente.", path);

        Problem problem = createProblemBuilder(status, problemType, detail).build();

        return handleExceptionInternal(ex, problem, headers, status, request);
    }

    @ExceptionHandler(NegocioException.class)
    public ResponseEntity<?> handleNegocioException(WebRequest request, NegocioException ex) {

        HttpStatus status = HttpStatus.BAD_REQUEST;
        ProblemType problemType = ProblemType.ERRO_NEGOCIO;
        String detail = ex.getMessage();

        Problem problem = createProblemBuilder(status, problemType, detail).build();

        return this.handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);

    }

    @ExceptionHandler(EntidadeNaoEncontradaException.class)
    public ResponseEntity<?> handleEntidadeNaoEncontrada(EntidadeNaoEncontradaException ex,
                                                         WebRequest request) {

        HttpStatus status = HttpStatus.NOT_FOUND;
        ProblemType problemType = ProblemType.RECURSO_NAO_ENCONTRADO;
        String detail = ex.getMessage();

        Problem problem = createProblemBuilder(status, problemType, detail).build();

        return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(EntidadeEmUsoException.class)
    public ResponseEntity<?> handleCozinhaNaoEncontradaException(WebRequest request, EntidadeEmUsoException ex) {

        HttpStatus status = HttpStatus.CONFLICT;
        ProblemType problemType = ProblemType.ENTIDADE_EM_USO;
        String detail = ex.getMessage();

        Problem problem = createProblemBuilder(status, problemType, detail).build();

        return this.handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);

    }

    //Continue fazendo tudo o que você já faz…
    //mas antes de responder, deixa eu ajustar o body
    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders
            headers, HttpStatus status, WebRequest request) {

        if (body == null) {

            body = Problem.builder()
                    .title(status.getReasonPhrase())
                    .status(status.value()).build();
        } else if (body instanceof String) {
            body = Problem.builder()
                    .title((String) body)
                    .status(status.value()).build();
        }
        return super.handleExceptionInternal(ex, body, headers, status, request);

    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleErrodeSistema (Exception ex, WebRequest request) {

        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        ProblemType problemType = ProblemType.ERRO_DE_SISTEMA;
        String detail = "Ocorreu um erro interno inesperado no sistema. Tente novamente e se o problema" +
        " persistir, entre em contato com o administrador do sistema.";

        Problem problem = createProblemBuilder(status, problemType, detail).build();

        return this.handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);

    }


    //Open/Closed Principle.
    //O método é fechado à modificação porque não preciso alterá-lo quando surgem novas variações;
    //ele é aberto à extensão porque posso enriquecer o resultado fora dele.
    public Problem.ProblemBuilder createProblemBuilder(HttpStatus status, ProblemType problemType, String detail){
        return Problem.builder()
                .status(status.value())
                .type(problemType.getUri())
                .title(problemType.getTitle())
                .detail(detail);
    }

    private String joinPath (List <JsonMappingException.Reference> references) {

        return references.stream()
                .map(reference -> reference.getFieldName())
                .collect(Collectors.joining("."));
    }

}