package com.algaworks.algafood;

import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.util.DatabaseCleaner;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;


@TestPropertySource(locations = "classpath:application-test.properties")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)//Spring escolhe uma porta disponível
public class CadastroCozinhaIT {

	//Injeta a porta aki
	@LocalServerPort
	private int port;

	@Autowired
	private DatabaseCleaner databaseCleaner;

	@Autowired
	private CozinhaRepository cozinhaRepository;
	@BeforeEach
	public void setUp(){
		RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
		RestAssured.port = port;
		RestAssured.basePath = "/cozinhas";

		databaseCleaner.clearTables();
		prepararDados();
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
	public void deveRetornar2Cozinhas_quandoConsultarCozinhas(){

		given()
				.accept(ContentType.JSON)
			.when()
				.get()
			.then()
				.body("nome", Matchers.hasSize(2))
				.body("nome", Matchers.hasItem("Japonesa"));//usado para Lista
	}

	@Test
	public void deveRetornarRespostaComStatusCorretos_QuandoConsultarCozinhaExistente(){
		given()
				.pathParam("cozinhaId", 1)
				.accept(ContentType.JSON)
			.when()
				.get("{cozinhaId}")
			.then()
				.statusCode(HttpStatus.OK.value())
				.body("nome", equalTo("Japonesa"));
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

	private void prepararDados(){
		Cozinha c1 = new Cozinha();
		c1.setNome("Japonesa");
		cozinhaRepository.save(c1);

		Cozinha c2 = new Cozinha();
		c2.setNome("Mexicana");
		cozinhaRepository.save(c2);
	}
}
