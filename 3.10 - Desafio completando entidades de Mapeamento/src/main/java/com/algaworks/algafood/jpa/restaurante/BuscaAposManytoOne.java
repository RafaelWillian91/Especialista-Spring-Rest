package com.algaworks.algafood.jpa.restaurante;

import com.algaworks.algafood.AlgafoodApiApplication;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import com.algaworks.algafood.infrastructure.repository.RestauranteRepositoryImpl;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

public class BuscaAposManytoOne {

    public static void main(String[] args) {

        ApplicationContext applicationContext = new SpringApplicationBuilder(AlgafoodApiApplication.class)
                .web(WebApplicationType.NONE)
                .run(args);

        RestauranteRepository restaurante = applicationContext.getBean(RestauranteRepositoryImpl.class);

        Restaurante r1 = restaurante.buscarRestauranteId(1L);

        System.out.printf("%s com a taxa de %f possue a cozinha %s \n",r1.getNome(), r1.getTaxaFrete(), r1.getCozinha().getNome());



    }

}
