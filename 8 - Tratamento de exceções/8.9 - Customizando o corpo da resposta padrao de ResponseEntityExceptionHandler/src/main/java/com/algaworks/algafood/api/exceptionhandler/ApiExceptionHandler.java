package com.algaworks.algafood.api.exceptionhandler;

import com.algaworks.algafood.domain.exception.CozinhaNaoEncontradaException;
import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.NegocioException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EntidadeNaoEncontradaException.class)
    public ResponseEntity<?> tratarEstadoNaoEncontradaException(WebRequest request, EntidadeNaoEncontradaException ex) {

        return this.handleExceptionInternal(ex, ex.getMessage(), new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(NegocioException.class)
    public ResponseEntity<?> tratarNegocioException(WebRequest request, NegocioException ex) {

        return this.handleExceptionInternal(ex, ex.getMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST, request);

    }


    @ExceptionHandler(EntidadeEmUsoException.class)
    public ResponseEntity<?> tratarCozinhaNaoEncontradaException(WebRequest request, EntidadeEmUsoException ex) {

        return this.handleExceptionInternal(ex, ex.getMessage(), new HttpHeaders(), HttpStatus.CONFLICT, request);

    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders
            headers, HttpStatus status, WebRequest request) {

        if (body == null) {

            body = Problema.builder()
                    .dataHora(LocalDateTime.now())
                    .mensagem(status.getReasonPhrase()).build();
        } else if (body instanceof String) {
            body = Problema.builder()
                    .dataHora(LocalDateTime.now())
                    .mensagem((String) body).build();
        }
        return super.handleExceptionInternal(ex, body, headers, status, request);

    }
}