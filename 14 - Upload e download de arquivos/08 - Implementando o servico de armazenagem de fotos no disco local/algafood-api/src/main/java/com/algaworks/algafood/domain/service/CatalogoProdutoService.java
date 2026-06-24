package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.model.FotoProduto;
import com.algaworks.algafood.domain.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class CatalogoProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Transactional
    public FotoProduto salvar(FotoProduto fotoProduto){

        Optional<FotoProduto> fotoExistente = produtoRepository.findFotoById(
                fotoProduto.getProduto().getRestaurante().getId(),
                fotoProduto.getProduto().getId());

        if(fotoExistente.isPresent()){
            produtoRepository.delete(fotoExistente.get());
        }

        return produtoRepository.save(fotoProduto);
    }

}
