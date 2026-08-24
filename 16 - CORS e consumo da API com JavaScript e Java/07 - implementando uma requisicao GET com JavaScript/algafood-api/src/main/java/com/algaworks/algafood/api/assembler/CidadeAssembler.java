package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.model.dtos.CidadeDTO;
import com.algaworks.algafood.domain.model.Cidade;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CidadeAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public  CidadeDTO toModel(Cidade cidade) {

        return modelMapper.map(cidade, CidadeDTO.class);
    }

    public List<CidadeDTO> toCollectionModel(List<Cidade> cidades) {

        return cidades.stream()
                .map(cidade -> toModel(cidade))
                .collect(Collectors.toList());
    }


}
