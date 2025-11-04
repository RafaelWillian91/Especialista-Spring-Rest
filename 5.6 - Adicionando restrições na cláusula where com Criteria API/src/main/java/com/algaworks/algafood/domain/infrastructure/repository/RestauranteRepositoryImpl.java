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
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
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

        //fabrica de elementos
        CriteriaBuilder builder = manager.getCriteriaBuilder();

        CriteriaQuery<Restaurante> criteria = builder.createQuery(Restaurante.class);

        //from Restaurante e a raiz é a variavel de instancia root
        Root<Restaurante> root = criteria.from(Restaurante.class);

                                            //nome dentro de root
        Predicate nomePredicate = builder.like(root.get("nome"), "%" + nome + "%");


        Predicate taxaInicialPredicate = builder
                //taxaFrete tem que ser maior ou igual a taxaFreteInicial
                .greaterThanOrEqualTo(root.get("taxaFrete"), taxaFreteInicial);

        Predicate taxaFinalPredicate = builder
                //taxaFrete tem que ser menor ou igual a taxaFreteFinal
                .lessThanOrEqualTo(root.get("taxaFrete"), taxaFreteFinal);

        //quando passo mais de um predicat ela faz and entre os parametros
        criteria.where(nomePredicate, taxaInicialPredicate, taxaFinalPredicate);

             TypedQuery <Restaurante> query =  manager.createQuery(criteria);

             return query.getResultList();
    }





}
