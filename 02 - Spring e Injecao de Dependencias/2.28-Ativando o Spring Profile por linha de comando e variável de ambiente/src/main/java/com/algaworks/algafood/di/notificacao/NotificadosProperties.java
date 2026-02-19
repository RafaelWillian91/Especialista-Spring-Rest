package com.algaworks.algafood.di.notificacao;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

//em vez de ficar usando @Value("${propriedade}") várias vezes,
// você agrupa várias propriedades relacionadas em uma única classe.
@Component
@ConfigurationProperties("notificador.email")
public class NotificadosProperties {

    /**
    * Host do e-mail
    */
    private String hostUrl; //representa, notificador.email.host-url=smtp.algafood.com.br, do arquivo de properties
    private Integer portPort;

    public String getHostUrl() {
        return hostUrl;
    }

    public void setHostUrl(String hostUrl) {
        this.hostUrl = hostUrl;
    }

    public Integer getPortPort() {
        return portPort;
    }

    public void setPortPort(Integer portPort) {
        this.portPort = portPort;
    }
}
