package com.algaworks.algafood;

import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.domain.service.CadastroCozinhaService;
import org.assertj.core.api.Assert;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintViolationException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("dev")
public class CadastroCozinhaIntegrationTests {

	@Autowired
	CadastroCozinhaService cozinhaService;
	@Test
	public void testarCadastroCozinhaSucesso(){
		//cenário
		Cozinha cozinha = new Cozinha();
		cozinha.setNome("restaurante-Teste");
		//acao
		cozinhaService.salvar(cozinha);
		//validacao
		Assertions.assertThat(cozinha).isNotNull();
		Assertions.assertThat(cozinha.getId()).isNotNull();
	}

	@Test
	public void testarCadastroCozinhaSemNome() {
		Cozinha novaCozinha = new Cozinha();
		novaCozinha.setNome(null);

		ConstraintViolationException erroEsperado =
				assertThrows(ConstraintViolationException.class, () -> {
					cozinhaService.salvar(novaCozinha);
				});

	}
}
