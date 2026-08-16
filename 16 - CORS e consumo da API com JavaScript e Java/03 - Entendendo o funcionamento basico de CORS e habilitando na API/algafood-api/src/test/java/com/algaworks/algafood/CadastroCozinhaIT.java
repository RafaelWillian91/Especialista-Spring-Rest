package com.algaworks.algafood;

import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.util.DatabaseCleaner;
import com.algaworks.algafood.util.ResourceUtils;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@RunWith(SpringRunner.class)
@TestPropertySource(locations = "classpath:application-test.properties")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)//Spring escolhe uma porta disponível
public class CadastroCozinhaIT {

	private static final int COZINHA_ID_INEXISTENTE = 1000;
	//Injeta a porta aki
	@LocalServerPort
	private int port;

	@Autowired
	private DatabaseCleaner databaseCleaner;

	private int quantidadeCozinhasCadastradas = 0;

	private static Cozinha cozinhaJaponesa;
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
	public void deveRetornarQuantidadeExataDeCozinhas_QuandoConsultarListaDeCozinhas(){

		given()
				.accept(ContentType.JSON)
			.when()
				.get()
			.then()
				.body("nome", Matchers.hasSize(quantidadeCozinhasCadastradas))
				.body("nome", Matchers.hasItem("Japonesa"));//usado para Lista
	}

	@Test
	public void deveRetornarRespostaComStatusCorretos_QuandoConsultarCozinhaExistente(){
		given()
				.pathParam("cozinhaId", cozinhaJaponesa.getId())
				.accept(ContentType.JSON)
			.when()
				.get("{cozinhaId}")
			.then()
				.statusCode(HttpStatus.OK.value())
				.body("nome", equalTo(cozinhaJaponesa.getNome()));
	}
	@Test
	public void deveRetornarStatus201QuandoCadastrarCozinha(){

		given()
				.body(ResourceUtils.getContentFromResource("/json/correto/boryParaCadastrarCozinha.json"))
				.contentType(ContentType.JSON)
				.accept(ContentType.JSON)
			.when()
				.post()
			.then()
				.statusCode(HttpStatus.CREATED.value());

	}

	@Test
	public void deveRetornarStatus404_QuandoConsultaCozinhaInexistente(){

		given()
				.pathParam("cozinhaID", COZINHA_ID_INEXISTENTE)
				.contentType(ContentType.JSON)
			.when()
				.get("/{cozinhaID}")
			.then()
				.statusCode(HttpStatus.NOT_FOUND.value());
	}

	private void prepararDados(){
		cozinhaJaponesa = new Cozinha();
		cozinhaJaponesa.setNome("Japonesa");
		cozinhaRepository.save(cozinhaJaponesa);


		Cozinha c2 = new Cozinha();
		c2.setNome("Mexicana");
		cozinhaRepository.save(c2);


		Cozinha c3 = new Cozinha();
		c3.setNome("Brasileira");
		cozinhaRepository.save(c3);

		quantidadeCozinhasCadastradas = (int) cozinhaRepository.count();

	}
}
