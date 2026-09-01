package com.algafood.client;

import com.algafood.client.api.RestauranteClient2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.client.RestClient;

@SpringBootApplication
public class ListaRestauranteMain2 {


    public static void main(String[] args) {

        ConfigurableApplicationContext configurableApplicationContext = SpringApplication.run(ListaRestauranteMain2.class);

        RestauranteClient2 restauranteClient2 = new RestauranteClient2(RestClient.create());



        restauranteClient2.listar()
                .stream()
                .forEach(s -> System.out.println("------->> " + s));


    }
}
