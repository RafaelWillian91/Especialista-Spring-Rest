package com.algaworks.algafood.jpa;

import com.algaworks.algafood.AlgafoodApiApplication;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.infrastructure.repository.CadastroCozinhaImpl;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

public class ExclusaoCozinhaMain {

	public static void main(String[] args) {
		ApplicationContext applicationContext = new SpringApplicationBuilder(AlgafoodApiApplication.class)
				.web(WebApplicationType.NONE)
				.run(args);

		CadastroCozinhaImpl cadastroCozinhaImpl = applicationContext.getBean(CadastroCozinhaImpl.class);

		Cozinha c1 = new Cozinha();
		c1.setId(1L);

		cadastroCozinhaImpl.remover(c1);
	}
	
}
