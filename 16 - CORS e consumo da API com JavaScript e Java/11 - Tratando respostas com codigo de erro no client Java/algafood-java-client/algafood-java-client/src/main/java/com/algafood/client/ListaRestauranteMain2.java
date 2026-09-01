package com.algafood.client;

import com.algafood.client.api.ClientApiException;
import com.algafood.client.api.RestauranteClient2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.client.RestClient;

@SpringBootApplication
public class ListaRestauranteMain2 {


    public static void main(String[] args) {

        ConfigurableApplicationContext configurableApplicationContext =
                SpringApplication.run(ListaRestauranteMain2.class);


        RestauranteClient2 restauranteClient21 = configurableApplicationContext.getBean(RestauranteClient2.class);
        try {
            restauranteClient21.listar()
                    .stream()
                    .forEach(s -> System.out.println("------->> " + s));
        }catch (ClientApiException e){
            System.out.println(e.getProblem());
        }



    }
}
