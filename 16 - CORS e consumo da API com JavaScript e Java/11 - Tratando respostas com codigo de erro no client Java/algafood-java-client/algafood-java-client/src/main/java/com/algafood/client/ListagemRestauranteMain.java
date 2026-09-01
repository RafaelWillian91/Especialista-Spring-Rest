package com.algafood.client;

import com.algafood.client.api.RestauranteClient;
import org.springframework.web.client.RestTemplate;

public class ListagemRestauranteMain {

    public static void main(String[] args) {

        RestTemplate restTemplate = new RestTemplate();

        RestauranteClient restauranteClient = new RestauranteClient(
                restTemplate, "http://localhost:8080");

        restauranteClient.listar()
                .stream()
                .forEach(s -> System.out.println(s));

    }
}
