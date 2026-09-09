package com.algafood.client.model;

import lombok.Data;

@Data
public class EnderecoModel {


    String cep;
    String logradouro;
    String numero;
    String complemento;
    String bairro;

    private CidadeResumoModel cidade;


}
