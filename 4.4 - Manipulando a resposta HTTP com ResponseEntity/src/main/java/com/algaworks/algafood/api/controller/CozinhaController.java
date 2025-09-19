package com.algaworks.algafood.api.controller;

import java.util.List;

import com.algaworks.algafood.api.model.CozinhasXMLWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;

@RestController
@RequestMapping("/cozinhas")
public class CozinhaController {

	@Autowired
	private CozinhaRepository cozinhaRepository;


	@GetMapping
	public List<Cozinha> listar() {

		return cozinhaRepository.listar();
	}

	//retorno embrulho de cozinha
	@GetMapping(produces = MediaType.APPLICATION_XML_VALUE)
	public CozinhasXMLWrapper listarXml(){
		return new CozinhasXMLWrapper(cozinhaRepository.listar());
	}

	@GetMapping("/{cozinhaId}")
	public ResponseEntity<Cozinha> buscaCozinha(@PathVariable Long cozinhaId) {

		Cozinha cozinha = cozinhaRepository.buscar(cozinhaId);

		//return ResponseEntity.status(HttpStatus.OK).body(cozinha);
		//return ResponseEntity.ok(cozinha);//atalho para linha de cima. Acurcar sintético

		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.LOCATION, "http://www.uol.com.br");
		return ResponseEntity
				.status(HttpStatus.FOUND)
				.headers(headers).build();
	}
	
}
