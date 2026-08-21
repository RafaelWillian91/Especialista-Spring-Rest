package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.model.input.CidadeInput;
import com.algaworks.algafood.api.model.input.CozinhaIdInput;
import com.algaworks.algafood.api.model.input.CozinhaInput;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Estado;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CozinhaDisassembler {

    @Autowired
    private ModelMapper modelMapper;


    public Cozinha toDomainObject(CozinhaInput cozinhaInput) {

        return modelMapper.map(cozinhaInput, Cozinha.class);
    }
    public void copyToDomainObject (CozinhaInput cozinhaInput, Cozinha cozinha){

        modelMapper.map(cozinhaInput, cozinha);
    }
}
