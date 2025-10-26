package com.algaworks.algafood.domain.infrastructure.repository;


import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.RestauranteRepositoryQueries;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.function.BiConsumer;

//o sufixo Impl é o “gancho mágico” do Spring Data JPA.
//Quando ele vê que existe uma interface RestauranteRepository e
// uma classe RestauranteRepositoryImpl, ele automaticamente associa essa implementação como extensão do repositório.
@Repository
public class RestauranteRepositoryImpl implements RestauranteRepositoryQueries {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public List<Restaurante> find(String nome, BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal){

        var jpql = new StringBuilder();
            jpql.append("from Restaurante where 1 = 1 ");

        var parametros = new HashMap<String, Object>();

            //se a String nao for nula nem vazia
        if(StringUtils.hasLength(nome)){
                jpql.append("and nome like :nome ");
                parametros.put("nome", "%" + nome + "%");
        }

        if (taxaFreteInicial != null) {
            jpql.append("and taxaFrete >= :taxaInicial ");
            parametros.put("taxaFrete", taxaFreteInicial);
        }

        if (taxaFreteFinal != null) {
            jpql.append("and taxaFrete <= :taxaFinal ");
            parametros.put("taxaFreteFinal", taxaFreteFinal);
        }


        TypedQuery<Restaurante> query =  manager
                .createQuery(jpql.toString(), Restaurante.class);

        parametros.forEach( (chave, valor) -> query.setParameter(chave, valor)  );

             return    query.getResultList();
    }





}
