package com.algafood.client.api;

import com.algafood.client.model.Problem;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.client.RestClientResponseException;
import tools.jackson.core.JacksonException;
import tools.jackson.databind.ObjectMapper;

@Slf4j
public class ClientApiException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    @Getter
    private Problem problem;
    public ClientApiException (String menssage, RestClientResponseException cause){
        super(menssage, cause);

        desserializeProblem(cause);
    }
    public ClientApiException (String menssage){
        super(menssage);

    }

    private void desserializeProblem(RestClientResponseException cause){
        ObjectMapper mapper = new ObjectMapper();
        try {
            this.problem = mapper.readValue(cause.getResponseBodyAsString(), Problem.class);
        }catch (JacksonException e){
            log.warn("Não foi possível desserializar o corpo de erro da API", e);
        }
    }
}
