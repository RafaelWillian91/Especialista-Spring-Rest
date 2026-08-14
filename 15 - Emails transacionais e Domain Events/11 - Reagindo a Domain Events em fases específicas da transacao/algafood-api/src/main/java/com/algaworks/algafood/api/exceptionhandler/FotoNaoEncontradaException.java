package com.algaworks.algafood.api.exceptionhandler;

import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;

public class FotoNaoEncontradaException extends EntidadeNaoEncontradaException {

    private static final long serialVersionUID = 1L;

    public FotoNaoEncontradaException(String mensagem) {
        super(mensagem);
    }

    public FotoNaoEncontradaException(Long restauranteId, Long produtoId) {
        this(String.format("Não existe um cadastro de foto do produto com código %d para o restaurante de código %d",
                produtoId, restauranteId));
    }
}
