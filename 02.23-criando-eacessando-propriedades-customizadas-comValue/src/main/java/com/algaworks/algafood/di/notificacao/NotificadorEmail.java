package com.algaworks.algafood.di.notificacao;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.di.modelo.Cliente;

@TipoDoNotificador(NivelUrgencia.SEM_URGENCIA)
@Component
public class NotificadorEmail implements Notificador {

	@Value("${notificador.email.host-url}")
	private String host;

	@Value("${notificador.email.port-port}")
	private Integer port;

	@Override
	public void notificar(Cliente cliente, String mensagem) {
		System.out.println("Host: " + host);
		System.out.println("Porta: " + port);
		System.out.printf("Notificando %s através do e-mail %s: %s\n", 
				cliente.getNome(), cliente.getEmail(), mensagem);
	}

}
