package com.algafood.client;

import com.algafood.client.api.ClientApiException;
import com.algafood.client.api.RestauranteCliente;
import com.algafood.client.model.RestauranteModel;
import com.algafood.client.model.input.CidadeInput;
import com.algafood.client.model.input.CozinhaInput;
import com.algafood.client.model.input.EnderecoInput;
import com.algafood.client.model.input.RestauranteInput;
import org.springframework.web.client.RestClient;

import java.math.BigDecimal;

public class RequisicaoPost {

    public static void main(String[] args) {
        try {

            RestClient restClient = RestClient.create();
            RestauranteCliente restauranteCliente = new RestauranteCliente(
                    restClient, "http://localhost:8080"
            );

            var cozinha = new CozinhaInput();
            cozinha.setId(1L);

            var cidade = new CidadeInput();
            cidade.setId(1L);

            var endereco = new EnderecoInput();
            endereco.setCidade(cidade);
            endereco.setCep("38500-111");
            endereco.setLogradouro("Rua Xyz");
            endereco.setNumero("300");
            endereco.setBairro("Centro");

            var restaurante = new RestauranteInput();
            restaurante.setNome("Comida Mineira");
            restaurante.setTaxaFrete(new BigDecimal(9.5));
            restaurante.setCozinha(cozinha);
            restaurante.setEndereco(endereco);

            RestauranteModel restauranteModel = restauranteCliente.requisicaoPost(restaurante);

            System.out.println(restauranteModel);
        } catch (ClientApiException e) {
            if (e.getProblem() != null) {
                System.out.println(e.getProblem().getUserMessage());

                e.getProblem().getObjects().stream()
                        .forEach(p ->  System.out.println("- " + p.getUserMessage()));

            } else {
                System.out.println("Erro desconhecido");
                e.printStackTrace();
            }
        }
    }


}
