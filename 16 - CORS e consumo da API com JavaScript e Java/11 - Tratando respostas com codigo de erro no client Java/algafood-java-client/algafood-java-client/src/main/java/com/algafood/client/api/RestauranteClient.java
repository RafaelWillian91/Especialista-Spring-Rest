package com.algafood.client.api;

import com.algafood.client.model.RestauranteModel;
import lombok.AllArgsConstructor;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@AllArgsConstructor
public class RestauranteClient {

    private static final String RESOURCE_PATH = "/restaurantes";
    private RestTemplate restTemplate;
    private String url;

    public List<RestauranteModel> listar(){

        URI resourceUri = URI.create(url + RESOURCE_PATH);

        RestauranteModel[] restauranteModels = restTemplate.getForObject(resourceUri, RestauranteModel[].class);

        return Arrays.asList(restauranteModels);
    }



}
