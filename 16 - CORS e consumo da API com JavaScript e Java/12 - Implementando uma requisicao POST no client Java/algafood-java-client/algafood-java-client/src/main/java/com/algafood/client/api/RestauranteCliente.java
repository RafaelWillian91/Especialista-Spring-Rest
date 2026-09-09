package com.algafood.client.api;

import com.algafood.client.model.RestauranteModel;
import com.algafood.client.model.input.RestauranteInput;
import lombok.AllArgsConstructor;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

import java.net.URI;

@AllArgsConstructor
public class RestauranteCliente {

    private static final String RESOURCE_PATH = "/restaurantes";

    private RestClient restClient;

    private String url;


    public RestauranteModel requisicaoPost(RestauranteInput restauranteInput){

        var resourceUrl = URI.create(url + RESOURCE_PATH);

        try {
            return restClient
                    .post()
                    .uri(resourceUrl)
                    .body(restauranteInput)
                    .retrieve()
                    .body(RestauranteModel.class);
        }catch (HttpClientErrorException e){
            throw new ClientApiException(e.getMessage(), e);
        }
    }



}
