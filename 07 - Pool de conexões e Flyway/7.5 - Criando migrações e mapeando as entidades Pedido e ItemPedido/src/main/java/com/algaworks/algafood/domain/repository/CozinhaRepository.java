package com.algaworks.algafood.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.algaworks.algafood.domain.model.Cozinha;

@Repository
public interface CozinhaRepository extends JpaRepository<Cozinha, Long> {

	//find  pode ter qualquer coisa no meio ou nada By(nome da propriedade) -> é um prefixo para busca
	//flag Containing para buscar qualquer coisa alem da proproiedade
	List<Cozinha> findTodasByNomeContaining(String nome);
	
	Optional<Cozinha> findByNome(String nome);

	boolean existsByNome(String nome);
}
