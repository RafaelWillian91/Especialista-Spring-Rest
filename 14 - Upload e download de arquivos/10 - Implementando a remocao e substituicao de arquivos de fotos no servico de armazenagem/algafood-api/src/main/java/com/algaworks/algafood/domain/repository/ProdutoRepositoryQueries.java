package com.algaworks.algafood.domain.repository;

import com.algaworks.algafood.domain.model.FotoProduto;


public interface ProdutoRepositoryQueries {

    public FotoProduto save(FotoProduto fotoProduto);
    void delete(FotoProduto fotoProduto);
}
