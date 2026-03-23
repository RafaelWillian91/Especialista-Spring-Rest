package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.model.input.RestauranteInput;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Restaurante;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RestauranteDisassembler {

    @Autowired
    private ModelMapper modelMapper;

    //Restaurante.class é um Destino
    public Restaurante toDomainObject(RestauranteInput restauranteInput) {

        return modelMapper.map(restauranteInput, Restaurante.class);

    }

    //Aqui Restaurante é uma instancia de um Objeto
    public void copyToDomainObject (RestauranteInput restauranteInput, Restaurante restaurante){

        //Para evitar: exception identifier of an instance of was altered from (id) to destiny (id)
        restaurante.setCozinha(new Cozinha());

        modelMapper.map(restauranteInput, restaurante);
    }

}
