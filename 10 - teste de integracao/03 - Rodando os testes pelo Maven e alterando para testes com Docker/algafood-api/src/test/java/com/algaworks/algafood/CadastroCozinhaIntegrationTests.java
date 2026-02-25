package com.algaworks.algafood;

import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.service.CadastroCozinhaService;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import javax.validation.ConstraintViolationException;
import static org.junit.jupiter.api.Assertions.assertThrows;


@SpringBootTest
@ActiveProfiles("test")
public class CadastroCozinhaIntegrationTests {

	@Autowired
	CadastroCozinhaService cozinhaService;

	@Autowired
	CadastroRestauranteService cadastroRestauranteService;
	@Test
	public void deveAtribuirId_QuandoCadastrarCozinhaComDadosCorretos(){
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
	public void deveFalhar_QuandoCadastrarCozinhaSemNome() {
		Cozinha novaCozinha = new Cozinha();
		novaCozinha.setNome(null);

		ConstraintViolationException erroEsperado =
				assertThrows(ConstraintViolationException.class, () -> {
					cozinhaService.salvar(novaCozinha);
				});

	}

	@Test
	public void deveFalhar_QuandoExcluirCozinhaEmUso(){

		Cozinha cozinhaUso = new Cozinha();
		cozinhaUso.setId(19L);
		Restaurante restaurante = new Restaurante();
		restaurante.setCozinha(cozinhaUso);

		NegocioException erroEsperado =
				assertThrows(NegocioException.class, () -> {
					cozinhaService.excluir(19L);
				});
	}

	@Test
	public void deveFalhar_QuandoExcluirCozinhaInexistente(){

		NegocioException erroEsperado =
				assertThrows(NegocioException.class, () -> {
					cozinhaService.excluir(60L);
				});
	}
}
