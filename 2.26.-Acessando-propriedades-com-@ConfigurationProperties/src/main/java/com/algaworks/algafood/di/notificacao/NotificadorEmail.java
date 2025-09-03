package com.algaworks.algafood.di.notificacao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.algaworks.algafood.di.modelo.Cliente;

@TipoDoNotificador(NivelUrgencia.SEM_URGENCIA)
@Component
public class NotificadorEmail implements Notificador {

	@Autowired
	//Injecao da classe de properties
	private NotificadosProperties notificadosProperties;
	@Override
	public void notificar(Cliente cliente, String mensagem) {
		System.out.println("Host: " + notificadosProperties.getHostUrl());// representa notificador.email.host-url=smtp.algafood.com.br
		System.out.println("Porta: " + notificadosProperties.getPortPort());
		System.out.printf("Notificando %s através do e-mail %s: %s\n", 
				cliente.getNome(), cliente.getEmail(), mensagem);
	}

}
