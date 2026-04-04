package com.algaworks.algafood.core.modelmapper;

import com.algaworks.algafood.api.model.dtos.EnderecoDTO;
import com.algaworks.algafood.api.model.dtos.RestauranteDTO;
import com.algaworks.algafood.domain.model.Endereco;
import com.algaworks.algafood.domain.model.Restaurante;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper(){
        var modelMapper = new ModelMapper();

        modelMapper.createTypeMap(Restaurante.class, RestauranteDTO.class)
                .addMapping(Restaurante::getTaxaFrete, RestauranteDTO::setPrecoFrete)
                .addMapping(
                        restSrc -> restSrc.getEndereco().getCidade().getEstado().getNome(),
                        (restDest, val) -> restDest.getEndereco().getCidade().setEstado((String) val)
                );

        return modelMapper;
    }


}
