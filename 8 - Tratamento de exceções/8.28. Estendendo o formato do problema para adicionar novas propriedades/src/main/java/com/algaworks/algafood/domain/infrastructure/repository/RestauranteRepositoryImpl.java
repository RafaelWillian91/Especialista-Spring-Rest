package com.algaworks.algafood.domain.infrastructure.repository;


import com.algaworks.algafood.domain.infrastructure.repository.spec.RestaurantesSpecs;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import com.algaworks.algafood.domain.repository.RestauranteRepositoryQueries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


//o sufixo Impl é o “gancho mágico” do Spring Data JPA.
//Quando ele vê que existe uma interface RestauranteRepository e
// uma classe RestauranteRepositoryImpl, ele automaticamente associa essa implementação como extensão do repositório.
@Repository
public class RestauranteRepositoryImpl implements RestauranteRepositoryQueries {

    @PersistenceContext
    private EntityManager manager;

    //@Lazy (Não inicialize esse bean (o Spring vai adiar a criação (ou a injeção) do objeto até o momento em que ele for necessário.
    //Isso é chamado de lazy initialization (inicialização preguiçosa)
    @Autowired @Lazy
    RestauranteRepository restauranteRepository;

    @Override
    public List<Restaurante> find(String nome, BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal){

        //fábrica de componentes de consulta
        CriteriaBuilder builder = manager.getCriteriaBuilder();

        //indica o tipo do resultado da consulta
        CriteriaQuery<Restaurante> criteria = builder.createQuery(Restaurante.class);

        //from Restaurante e a raiz é a variavel de instancia root.Informamos de qual entidade (tabela) os dados virão
        Root<Restaurante> root = criteria.from(Restaurante.class);

        var predicates = new ArrayList<Predicate>();

        if(StringUtils.hasText(nome)){
            predicates.add(builder.like(root.get("nome"), "%" + nome + "%"));
        }

        if(taxaFreteInicial != null){
            predicates.add(builder
                    .greaterThanOrEqualTo(root.get("taxaFrete"), taxaFreteInicial));
        }

        if(taxaFreteFinal != null){
            predicates.add(builder
                    .lessThanOrEqualTo(root.get("taxaFrete"), taxaFreteFinal));
        }


        //é aqui que você diz quais condições (Predicates) o JPA deve colocar no SQL final.
        criteria.where(predicates.toArray(new Predicate[0]));

             TypedQuery <Restaurante> query =  manager.createQuery(criteria);

             return query.getResultList();
    }

    @Override
    public List<Restaurante> findComFreteGratis(String nome) {
        return restauranteRepository.findAll(RestaurantesSpecs.comFrteGratis()
                .and(RestaurantesSpecs.comNomeSemelhante(nome)));
    }


}
