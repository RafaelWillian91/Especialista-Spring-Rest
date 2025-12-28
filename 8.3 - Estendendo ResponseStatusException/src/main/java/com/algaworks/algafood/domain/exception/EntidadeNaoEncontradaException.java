package com.algaworks.algafood.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.UnsupportedMediaTypeStatusException;

public class EntidadeNaoEncontradaException extends ResponseStatusException {

	private static final long serialVersionUID = 1L;

	//tenho a liberdade de mudar o HttpStatus e passar uma nova mensagem atraves desse construtor
	public EntidadeNaoEncontradaException(HttpStatus status, String mensagem) {
		super(status, mensagem);
	}

	//se a classe for instanciada por esse construtor ela automaticamente assume o HTTPSTATUS.NOT_FOUND e uma mensagem.
	public EntidadeNaoEncontradaException(String mensagem) {
		this(HttpStatus.NOT_FOUND, mensagem);
	}
}
