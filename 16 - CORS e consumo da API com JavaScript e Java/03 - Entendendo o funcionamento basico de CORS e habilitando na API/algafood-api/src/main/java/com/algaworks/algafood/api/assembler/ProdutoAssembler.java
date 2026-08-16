package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.model.dtos.ProdutoDTO;
import com.algaworks.algafood.domain.model.Produto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProdutoAssembler {

    @Autowired
    private ModelMapper modelMapper;


    public ProdutoDTO toModel(Produto produto){
        return modelMapper.map(produto, ProdutoDTO.class);
    }

    public List<ProdutoDTO> toCollectionModel(List<Produto> produto){
        return produto.stream()
                .map(this::toModel)
                .toList();
    }

}
