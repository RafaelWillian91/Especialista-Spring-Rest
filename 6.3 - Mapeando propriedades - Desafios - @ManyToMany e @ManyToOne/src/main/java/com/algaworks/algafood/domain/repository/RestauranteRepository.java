package com.algaworks.algafood.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.algaworks.algafood.domain.model.Restaurante;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface RestauranteRepository extends CustomJpaRepository<Restaurante, Long>, RestauranteRepositoryQueries, JpaSpecificationExecutor<Restaurante> {

    List<Restaurante> findByTaxaFreteBetween (BigDecimal taxaInicial, BigDecimal taxaFinal);

    //@Query("from Restaurante where nome like %:nome% and cozinha.id = :id")
   // @Query("from Restaurante where nome like concat('%', :nome, '%') and cozinha.id = :id")
    @Query(name = "Restaurante.consultarPorNome")
    List<Restaurante> consultarPorNome (@Param("nome") String name,  Long id);

    //é possivel colocar uma flag no querymethod para limitar o retorno da consulta
    Optional<Restaurante> findFirstRestauranteByNomeContaining (String nome);
    List<Restaurante> findTop2ByNomeContaining(String nome);

    //existe prefixos como count e exists para retorno pronto de métodos
    int countByCozinhaId(Long cozinha);


}
