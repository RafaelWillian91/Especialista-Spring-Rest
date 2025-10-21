package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/teste")
public class TesteControler {

    private CozinhaRepository cozinhaRepository;

    @GetMapping("/cozinha/por-nome")
    public List<Cozinha> cozinhaPorNome(@RequestParam("nome")String nome){
        return cozinhaRepository.consultarPorNome(nome);
    }



}
