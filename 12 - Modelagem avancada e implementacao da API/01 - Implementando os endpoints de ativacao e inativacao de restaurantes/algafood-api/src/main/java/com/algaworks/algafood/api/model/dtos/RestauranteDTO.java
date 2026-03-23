package com.algaworks.algafood.api.model.dtos;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RestauranteDTO {

    private Long id;
    private String nome;
    private BigDecimal precoFrete;
    private CozinhaDTO cozinha;
    private Boolean ativo;

}
