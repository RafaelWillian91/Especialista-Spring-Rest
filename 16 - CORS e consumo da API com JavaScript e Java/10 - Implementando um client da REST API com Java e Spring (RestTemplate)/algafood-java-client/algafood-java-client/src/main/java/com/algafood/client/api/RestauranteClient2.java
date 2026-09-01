package com.algafood.client.api;

import com.algafood.client.model.RestauranteModel;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.Arrays;
import java.util.List;

@AllArgsConstructor
@Component
public class RestauranteClient2 {

    private static final String RESOURCE_PATH = "/restaurantes";

    private final RestClient restClient;

    public List<RestauranteModel> listar() {


        RestauranteModel[] restauranteModels = restClient
                .get()
                .uri(RESOURCE_PATH)
                .retrieve()
                .body(RestauranteModel[].class);



        return Arrays.asList(restauranteModels);
    }
}
