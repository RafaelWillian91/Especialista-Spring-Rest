package com.algaworks.algafood.domain.infrastructure.repository.spec;

import com.algaworks.algafood.domain.model.Restaurante;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.math.BigDecimal;

@AllArgsConstructor
public class RestaurantecomNomeSemelhanteSpec implements Specification<Restaurante> {


    //O root representa a entidade principal da consulta — é o mesmo que o FROM do SQL.Com ele, você pode acessar os atributos da entidade

    /* CriteriaQuery<?> query
    Você raramente mexe nela, mas pode usar para:
    Alterar o tipo de resultado (query.select(...));
    Fazer fetch joins (root.fetch("cozinha"));
    Aplicar ordenação (query.orderBy(...)).
     */

    /* CriteriaBuilder criteriaBuilder
    É a fábrica de expressões e condições (Predicates).
    Você usa ela pra construir as partes do WHERE.
    Ela tem vários métodos úteis:
        Método	Exemplo	SQL gerado
        equal(a, b)	criteriaBuilder.equal(root.get("id"), 1)	SQL = id = 1
        like(a, b)	criteriaBuilder.like(root.get("nome"), "%Pizza%")	SQL = nome LIKE '%Pizza%'
        greaterThan(a, b)	criteriaBuilder.greaterThan(root.get("taxaFrete"), 10)	SQL = taxa_frete > 10
        and(p1, p2)	criteriaBuilder.and(p1, p2)  SQL = (A AND B)
        or(p1, p2)	criteriaBuilder.or(p1, p2)	 SQL = (A OR B)
       */
    private String nome;
    @Override
    public Predicate toPredicate(Root<Restaurante> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

        return criteriaBuilder.like(root.get("nome"), "%" + nome + "%");
    }
}
