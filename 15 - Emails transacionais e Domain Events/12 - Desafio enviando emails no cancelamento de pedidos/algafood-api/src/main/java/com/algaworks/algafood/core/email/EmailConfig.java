package com.algaworks.algafood.core.email;

import com.algaworks.algafood.domain.service.EnvioEmailService;
import com.algaworks.algafood.infrastructure.service.email.EnvioEmailFake;
import com.algaworks.algafood.infrastructure.service.email.SandBoxEnvioEmailService;
import com.algaworks.algafood.infrastructure.service.email.SmtpEnvioEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EmailConfig {

    @Autowired
    private EmailProperties emailProperties;

    @Bean
    public EnvioEmailService envioEmailFake (){

        if(EmailProperties.EmailTipoEnvio.SMTP.equals(emailProperties.getImpl())){
            return new SmtpEnvioEmailService();
        }else if (EmailProperties.EmailTipoEnvio.FAKE.equals(emailProperties.getImpl()))
        {
            return new EnvioEmailFake();
        }else {
            return new SandBoxEnvioEmailService();
        }

    }

}
