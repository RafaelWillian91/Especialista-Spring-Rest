package com.algaworks.algafood.domain.repository;

import com.algaworks.algafood.domain.model.Cozinha;

import java.util.List;
//padrao reconhecido mundialmente. O Repository funciona como uma abstração da camada de persistência,
// garantindo que o modelo de domínio fique limpo, sem “sujeira” de código de banco ou de infra.
//Quando vc cria o padrao repository vc nao cria um repositorio por tabela ou entidade. O repositorio é por AgregateRoot, esse é o ideal.
public interface CozinhaRepository {


    List<Cozinha> listar();

    Cozinha buscarCozinha(Long cozinhaID);

    Cozinha addCozinha(Cozinha cozinha);

    void remover (Cozinha cozinha);

}
