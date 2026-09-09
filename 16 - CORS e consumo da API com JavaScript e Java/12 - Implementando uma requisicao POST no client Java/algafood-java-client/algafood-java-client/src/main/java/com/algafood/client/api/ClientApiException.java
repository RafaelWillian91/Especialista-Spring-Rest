package com.algafood.client.api;

import com.algafood.client.model.Problem;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.client.RestClientResponseException;
import tools.jackson.core.JacksonException;
import tools.jackson.databind.ObjectMapper;


@Slf4j
public class ClientApiException extends RuntimeException{

    @Getter
    private Problem problem;
    public ClientApiException(String message, RestClientResponseException  cause) {
        super(message, cause);

        deserializeProblem(cause);
    }

    private void deserializeProblem(RestClientResponseException restClientResponseException){

        ObjectMapper objectMapper = new ObjectMapper();

        try {
            this.problem = objectMapper.readValue(restClientResponseException.getResponseBodyAsString(), Problem.class);

        }catch (JacksonException e){
            log.warn("Não foi possível desserializar a resposta em um problema", e);
        }

    }
}
