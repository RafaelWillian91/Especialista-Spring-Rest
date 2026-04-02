package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.model.input.CidadeInput;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.Estado;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CidadeDisassembler {

    @Autowired
    private ModelMapper modelMapper;

    //Restaurante.class é um Destino
    public Cidade toDomainObject(CidadeInput cidadeInput) {

        return modelMapper.map(cidadeInput, Cidade.class);

    }

    //Aqui Restaurante é uma instancia de um Objeto
    public void copyToDomainObject (CidadeInput cidadeInput, Cidade cidade){

        //Para evitar: exception identifier of an instance of was altered from (id) to destiny (id)
        cidade.setEstado(new Estado());

        modelMapper.map(cidadeInput, cidade);
    }

}
