package com.algaworks.algafood.api.assembler;


import com.algaworks.algafood.api.model.input.GrupoInput;
import com.algaworks.algafood.domain.model.Grupo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GrupoDisassembler {

    @Autowired
    private ModelMapper modelMapper;

    public Grupo toModel(GrupoInput grupoInput){
       return modelMapper.map(grupoInput, Grupo.class);
    }

    public void toCopy (GrupoInput grupoInput, Grupo grupoBd){
        modelMapper.map(grupoInput, grupoBd);
    }

}
