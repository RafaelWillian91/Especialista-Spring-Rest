package com.algaworks.algafood.api.exceptionhandler;

import lombok.Getter;


public enum ProblemType {

        MENSAGEM_INCOMPREENSIVEL("mensagem-imcompreensivel", "Mensagem imcompreensível"),
        RECURSO_NAO_ENCONTRADO("recurso-nao-encontrado", "Recurso não encontrado"),
        ENTIDADE_EM_USO("entida-em-uso", "Entidade em uso"),
        ERRO_NEGOCIO("erro-de-Negócio", "Violação de regra de Negócio"),
        PARAMETRO_INVALIDO("erro-de-parametro", "Corrija o parâmetro informado"),

        ERRO_DE_SISTEMA("erro-de-sistema", "ERRO_DE_SISTEMA");

        private String title;
        private String uri;

        private ProblemType (String path, String title){
            this.uri = "https://algafood.com/" + path;
            this.title = title;
        }

    public String getUri() {
        return uri;
    }

    public String getTitle() {
        return title;
    }
}
