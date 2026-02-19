package com.algaworks.algafood.domain.repository;

import com.algaworks.algafood.domain.model.Restaurante;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface RestauranteRepository {

    List<Restaurante> listaRestaurante();


    Restaurante addRestaurante(Restaurante restaurante);

    Restaurante buscarRestauranteId(Long id);

    void removerRestaurante();

}
