package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.model.dtos.CozinhaDTO;
import com.algaworks.algafood.api.model.dtos.FotoProdutoDTO;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.FotoProduto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class FotoProdutoAssembler {


    @Autowired
    private ModelMapper modelMapper;

    public FotoProdutoDTO toModel(FotoProduto fotoProduto) {

        return modelMapper.map(fotoProduto, FotoProdutoDTO.class);
    }

}
