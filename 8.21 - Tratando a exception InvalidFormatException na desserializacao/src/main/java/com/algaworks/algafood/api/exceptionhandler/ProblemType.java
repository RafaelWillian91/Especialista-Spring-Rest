package com.algaworks.algafood.api.exceptionhandler;

import lombok.Getter;


public enum ProblemType {

        MENSAGEM_INCOMPREENSIVEL("mensagem-imcompreensivel", "Mensagem imcompreensível"),
        ENTIDADE_NAO_ENCONTRADA("entidade-nao-encontrada", "Entidade não encontrada"),
        ENTIDADE_EM_USO("entida-em-uso", "Entidade em uso"),
        ERRO_NEGOCIO("erro-de-Negócio", "Violação de regra de Negócio");

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
