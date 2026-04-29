package com.algaworks.algafood.domain.exception;

public class itemNaoEncontradoException extends EntidadeNaoEncontradaException{

    private static final long serialVersionUID = 1L;

    public itemNaoEncontradoException(String mensagem) {
        super(mensagem);
    }

    public itemNaoEncontradoException(Long grupoId) {
        this(String.format("Não existe um cadastro de item com o id %d", grupoId));
    }
}

