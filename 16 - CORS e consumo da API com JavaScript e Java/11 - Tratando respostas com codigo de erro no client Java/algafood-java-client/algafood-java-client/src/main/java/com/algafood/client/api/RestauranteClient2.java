package com.algafood.client.api;

import com.algafood.client.model.RestauranteModel;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientResponseException;

import java.util.Arrays;
import java.util.List;

@AllArgsConstructor
@Component
public class RestauranteClient2 {

    private static final String RESOURCE_PATH = "/restaurantesp";

    private final RestClient restClient;

    public List<RestauranteModel> listar() {

        try {
            RestauranteModel[] restauranteModels = restClient
                    .get()
                    .uri(RESOURCE_PATH)
                    .retrieve()
                    .body(RestauranteModel[].class);


            return Arrays.asList(restauranteModels);
        }catch (RestClientResponseException e){
            throw new ClientApiException(e.getMessage(), e);
        }catch (ResourceAccessException e){
            throw new ClientApiException("Não foi possível acessar a API");
        }
    }
}
