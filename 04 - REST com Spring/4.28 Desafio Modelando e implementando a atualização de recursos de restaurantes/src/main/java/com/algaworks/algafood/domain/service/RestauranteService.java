package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.RestauranteNaoencontradoException;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RestauranteService {

    @Autowired
    private RestauranteRepository restauranteRepository;


    public List<Restaurante> listaRestaurante(){
        return restauranteRepository.listar();
    }

    public Restaurante buscaRestauranteID(Long id){

        Restaurante restaurante = restauranteRepository.buscar(id);

        if(restaurante == null){
            throw new RestauranteNaoencontradoException("O restaurante não foi encontrado");
        }
        return restaurante;
    }

}
