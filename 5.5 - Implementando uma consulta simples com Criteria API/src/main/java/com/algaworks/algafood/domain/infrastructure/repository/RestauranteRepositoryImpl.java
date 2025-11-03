package com.algaworks.algafood.domain.infrastructure.repository;


import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.RestauranteRepositoryQueries;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;


//o sufixo Impl é o “gancho mágico” do Spring Data JPA.
//Quando ele vê que existe uma interface RestauranteRepository e
// uma classe RestauranteRepositoryImpl, ele automaticamente associa essa implementação como extensão do repositório.
@Repository
public class RestauranteRepositoryImpl implements RestauranteRepositoryQueries {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public List<Restaurante> find(String nome, BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal){

        CriteriaBuilder builder = manager.getCriteriaBuilder();

        CriteriaQuery<Restaurante> criteria = builder.createQuery(Restaurante.class);

        //from Restaurante
        criteria.from(Restaurante.class);


             TypedQuery <Restaurante> query =  manager.createQuery(criteria);

             return query.getResultList();
    }





}
