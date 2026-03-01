package com.algaworks.algafood;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.flywaydb.core.Flyway;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;

import static io.restassured.RestAssured.given;



@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)//Spring escolhe uma porta disponível
public class CadastroCozinhaIT {

	//Injeta a porta aki
	@LocalServerPort
	private int port;

	@Autowired
	private Flyway flyway;

	//metodo de fallback. É executado antes de cada teste
	@BeforeEach
	public void setUp(){
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
		RestAssured.port = port;
		RestAssured.basePath = "/cozinhas";

		//Limpeza no Banco para não dar inconsistencia de dados
		flyway.migrate();
	}
	@Test
	public void deveRetornarStatus200_quandoConsultarCozinhas(){

		given()
				.accept(ContentType.JSON)
			.when()
				.get()
			.then()
				.statusCode(200);

	}
	@Test
	public void deveRetornar4Cozinhas_quandoConsultarCozinhas(){

		given()
				.accept(ContentType.JSON)
			.when()
				.get()
			.then()
				.body("nome", Matchers.hasSize(4))
				.body("nome", Matchers.hasItem("Indiana"));
	}

	@Test
	public void deveRetornarStatus201QuandoCadastrarCozinha(){

		given()
				.body("{ \"nome\": \"Chinesa\" }" )
				.contentType(ContentType.JSON)
				.accept(ContentType.JSON)
			.when()
				.post()
			.then()
				.statusCode(HttpStatus.CREATED.value());

	}
}
