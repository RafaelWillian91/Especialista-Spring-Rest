package com.algaworks.algafood.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;

@RestController
@RequestMapping("/cozinhas")
//@RequestMapping(value = "/cozinhas", produces = MediaType.APPLICATION_JSON_VALUE) todos os metodos do controlador so aceitam Json
public class CozinhaController {

	@Autowired
	private CozinhaRepository cozinhaRepository;

	//@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE) adiciono a restricao apenas no método
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Cozinha> listar() {

		return cozinhaRepository.listar();
	}
	
}
