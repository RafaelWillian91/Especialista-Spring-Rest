package com.algaworks.algafood.domain.service;

import lombok.Getter;

import java.util.Set;

public interface EnvioEmailService {

    void enviar(Mensagem mensagem);

    @Getter
    class Mensagem {
        private Set<String> destinatarios;
        private String assunto;
        private String corpo;

    }

}
