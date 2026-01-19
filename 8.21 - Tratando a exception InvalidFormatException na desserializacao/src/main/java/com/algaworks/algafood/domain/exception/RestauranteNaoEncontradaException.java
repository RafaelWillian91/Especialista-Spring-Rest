package com.algaworks.algafood.domain.exception;


public class RestauranteNaoEncontradaException extends EntidadeNaoEncontradaException {
	private static final long serialVersionUID = 1L;
	public RestauranteNaoEncontradaException(String mensagem) {
		super(mensagem);
	}
	public RestauranteNaoEncontradaException(Long restauranteId) {
		this(String.format("Nao existe Restaurante com o código %d", restauranteId));
	}
}
