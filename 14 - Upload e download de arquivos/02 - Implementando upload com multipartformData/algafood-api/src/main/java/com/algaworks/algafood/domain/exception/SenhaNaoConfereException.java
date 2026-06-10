package com.algaworks.algafood.domain.exception;

public class SenhaNaoConfereException extends NegocioException{
    private static final long serialVersionUID = 1L;

    public SenhaNaoConfereException(String senhaNaoConfere) {
        super(senhaNaoConfere);
    }


}
