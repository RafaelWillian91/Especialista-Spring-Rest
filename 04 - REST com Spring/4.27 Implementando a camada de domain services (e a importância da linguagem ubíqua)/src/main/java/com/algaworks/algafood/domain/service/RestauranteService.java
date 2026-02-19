package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.RestauranteNaoencontradoException;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RestauranteService {

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private CozinhaRepository cozinhaRepository;

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








//    public Restaurante alterarRestaurante(Restaurante restaurante){
//
//        Long restauranteId1 =  restaurante.getId();
//        Restaurante restaurante1 = restauranteRepository.buscar(restauranteId1);
//
//        Long restauranteId = restaurante.getCozinha().getId();
//        List<Cozinha> cozinhas = cozinhaRepository.listar();
//
//        Optional a = cozinhas.stream().filter(
//                cozinha -> cozinha.getId().equals(restaurante.getCozinha().getId())
//        ).findFirst();
//
//        if(restaurante1 == null){
//            throw  new EntidadeNaoEncontradaException("O ID do Restaurante não existe");
//        }
//        else if (!a.isPresent() ){
//            throw  new EntidadeNaoEncontradaException("O ID da Cozinha não existe");
//        }
//
//        return restauranteRepository.salvar(restaurante);
//
//    }



}


