package com.algaworks.algafood.api.exceptionhandler;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.NegocioException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice                        //um tratador global de exceções
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        ProblemType problemType = ProblemType.MENSAGEM_INCOMPREENSIVEL;
        String detail = "O corpo da requisição esta inválido. Verifique o erro de sintaxe.";

        Problem problem = createProblemBuilder(status, problemType, detail).build();

        return this.handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(EntidadeNaoEncontradaException.class)
    public ResponseEntity<?> handleEstadoNaoEncontradaException(WebRequest request, EntidadeNaoEncontradaException ex) {

        HttpStatus status = HttpStatus.NOT_FOUND;
        ProblemType problemType = ProblemType.ENTIDADE_NAO_ENCONTRADA;
        String detail = ex.getMessage();

        Problem problem = createProblemBuilder(status, problemType, detail).build();

//        Problem problem = Problem.builder()
//                .status(status.value())
//                .type("https://algafood.com.br/entidade-nao-encontrada")
//                .title("Entidade não encontrada")
//                .detail(ex.getMessage())
//                .build();

        return this.handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(NegocioException.class)
    public ResponseEntity<?> handleNegocioException(WebRequest request, NegocioException ex) {

        HttpStatus status = HttpStatus.BAD_REQUEST;
        ProblemType problemType = ProblemType.ERRO_NEGOCIO;
        String detail = ex.getMessage();

        Problem problem = createProblemBuilder(status, problemType, detail).build();

        return this.handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);

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




}