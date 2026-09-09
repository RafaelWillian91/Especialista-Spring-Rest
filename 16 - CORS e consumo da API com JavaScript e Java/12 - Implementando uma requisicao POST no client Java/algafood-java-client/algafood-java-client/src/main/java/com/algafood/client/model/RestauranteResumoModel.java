package com.algafood.client.model;

import com.algafood.client.model.CozinhaModel;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class RestauranteResumoModel {


        private Long id;
        private String nome;
        private BigDecimal taxaFrete;
        private CozinhaModel cozinha;


}
