package com.algaworks.algafood.infrastructure.service.email;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Slf4j
public class EnvioEmailFake extends SmtpEnvioEmailService {
    @Override
    public void enviar(Mensagem mensagem) {

        String corpo = processarTemplate(mensagem);

        log.info("[FAKE E-MAIL] Para: {}\n{}", mensagem.getDestinatarios(), corpo);
    }
}
