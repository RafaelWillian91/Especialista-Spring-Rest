package com.algafood.client.model.input;

import lombok.Builder;
import lombok.Data;

@Data
public class EnderecoInput {


    String cep;
    String logradouro;
    String numero;
    String complemento;
    String bairro;

    private CidadeInput cidade;


}
