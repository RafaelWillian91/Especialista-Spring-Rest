package com.algaworks.algafood.api.model.dtos;

import java.math.BigDecimal;

import com.algaworks.algafood.api.model.view.RestauranteView;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RestauranteDTO {

    @JsonView ({RestauranteView.Resumo.class, RestauranteView.ApenasNomes.class })
    private Long id;
    @JsonView ({RestauranteView.Resumo.class, RestauranteView.ApenasNomes.class })
    private String nome;
    @JsonView (RestauranteView.Resumo.class)
    private BigDecimal precoFrete;
    @JsonView (RestauranteView.Resumo.class)
    private CozinhaDTO cozinha;
    private Boolean ativo;
    private Boolean aberto;
    private EnderecoDTO endereco;



}
