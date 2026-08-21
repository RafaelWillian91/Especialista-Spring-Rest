package com.algaworks.algafood.core.web;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class webConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**");
//                .allowedOrigins("*") - aceita requisições cross-origin vindas de qualquer origem.
//                .allowedMethods("*") - permite todos os métodos HTTP contemplados pela configuração CORS.
//                .maxAge(30); - permite ao navegador manter o resultado do preflight em cache por 30 segundos
    }
}
