package com.algafood.client.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class RestauranteModel {

    private Long id;
    private String nome;

    private BigDecimal precoFrete;

    private CozinhaModel cozinha;


}
