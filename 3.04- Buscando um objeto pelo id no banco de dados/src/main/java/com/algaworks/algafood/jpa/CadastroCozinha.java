package com.algaworks.algafood.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.model.Cozinha;

@Component
public class CadastroCozinha {

	//@PersistenceContext garante que o EntityManager injetado é gerenciado pelo container
	@PersistenceContext
	private EntityManager manager; //EntityManager é o equivalente ao JDBC Connection, mas no mundo da JPA.
	//O EntityManager é contextual, ou seja, dentro de uma transação, ele mantém um cache de primeiro nível (as entidades já carregadas).
	//Isso significa que se você buscar a mesma entidade duas vezes dentro da mesma transação, ele nem vai no banco de novo — ele devolve do cache
	public List<Cozinha> listar() {
		return manager.createQuery("from Cozinha", Cozinha.class)
				.getResultList();
	}

	//Até funciona sem @Transaction, mas a Entidade retornada esta fora do contexto do EntityManager, com isso
	//Perdemos Cache de primeiro nível: se você fizer dois find() para o mesmo ID na mesma transação, só o primeiro gera SELECT.
	public Cozinha buscarCozinha(Long id){
		return manager.find(Cozinha.class, id);
	}

	//O @Transactional do Spring abre e fecha uma transação para o método.
	@Transactional
	public Cozinha adicionar(Cozinha cozinha) {
		//colocar a entidade no contexto de persistencia.
		return manager.merge(cozinha);
	}
	
}
