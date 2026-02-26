package com.algaworks.algafood;

import static io.restassured.RestAssured.given;

import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.service.CadastroCozinhaService;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;
import io.restassured.RestAssured;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import javax.validation.ConstraintViolationException;
import static org.junit.jupiter.api.Assertions.assertThrows;



@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)//Spring escolhe uma porta disponível
public class CadastroCozinhaIT {

	private final static RestAssured restAssured = null;

	//Injeta a porta aki
	@LocalServerPort
	private int port;
	@Test
	public void deveRetornarStatus200_quandoConsultarCozinhas(){
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();

		given()
				.basePath("/cozinhas")
				//A porta é passada aki
				.port(port)
				.when()
				.get()
				.then()
				.statusCode(201);

	}
}
