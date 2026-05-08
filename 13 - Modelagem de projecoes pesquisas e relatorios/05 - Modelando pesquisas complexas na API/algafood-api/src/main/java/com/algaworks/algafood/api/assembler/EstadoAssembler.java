package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.model.dtos.EstadoDTO;
import com.algaworks.algafood.domain.model.Estado;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EstadoAssembler {

    @Autowired
    private ModelMapper modelMapper;


    public EstadoDTO toModel(Estado estado){
        return modelMapper.map(estado, EstadoDTO.class);
    }

    public List<EstadoDTO> toCollectionDTO (List<Estado> estados){

        return estados.stream()
                .map(estado -> toModel(estado))
                .toList();
    }
}
