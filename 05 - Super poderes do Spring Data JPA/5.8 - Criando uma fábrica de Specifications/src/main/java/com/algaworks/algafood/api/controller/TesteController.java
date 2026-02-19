package com.algaworks.algafood.api.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import com.algaworks.algafood.domain.infrastructure.repository.spec.RestaurantesSpecs;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;

@RestController
@RequestMapping("/teste")
public class TesteController {

	@Autowired
	private CozinhaRepository cozinhaRepository;

	@Autowired
	private RestauranteRepository restauranteRepository;
	
	@GetMapping("/cozinhas/por-nome")
	public List<Cozinha> cozinhasPorNome(@RequestParam String nome) {

		return cozinhaRepository.findTodasByNomeContaining(nome);
	}
	
	@GetMapping("/cozinhas/unica-por-nome")
	public Optional<Cozinha> cozinhaPorNome(String nome) {
		return cozinhaRepository.findByNome(nome);
	}


	@GetMapping("/restaurante/por-frete")
	public List<Restaurante> restaurantePorTaxaFrete (BigDecimal a, BigDecimal b) {
		return restauranteRepository.findByTaxaFreteBetween(a, b);
	}


	@GetMapping("/restaurante/por-nome")
	public List<Restaurante> restaurantePorTaxaFrete (String nome,@RequestParam("b") Long id) {

		return restauranteRepository.consultarPorNome(nome, id);
	}

	@GetMapping("/restaurante/primeiro-por-nome")
	public Optional<Restaurante> restaurantePorNomePrimeiro (String nome) {
		return restauranteRepository.findFirstRestauranteByNomeContaining(nome);
	}

	@GetMapping("/restaurante/top2-por-nome")
	public List<Restaurante> resturanteTop2 (String nome) {
		return restauranteRepository.findTop2ByNomeContaining(nome);
	}

	@GetMapping("/cozinhas/cozinha-existe")
	public boolean restauranteexistente (String nome) {
		return cozinhaRepository.existsByNome(nome);
	}

	@GetMapping("/restaurantes/contando-restaurantes")
	public int restauranteexistente (Long id) {
		return restauranteRepository.countByCozinhaId(id);
	}


	@GetMapping("/restaurante/por-nome-e-frete")
	public List<Restaurante> restaurantesPorNomeFrete(String nome, BigDecimal taxaFreteInicial,
													  BigDecimal taxaFreteFinal){
		return restauranteRepository.find(nome, taxaFreteInicial, taxaFreteFinal);
	}

	@GetMapping("/restaurantes/com-frete-gratis")
	public List<Restaurante> restaurantesComFretesGratis(String nome){

		return restauranteRepository.findAll(RestaurantesSpecs.comFrteGratis()
										.and(RestaurantesSpecs.comNomeSemelhante(nome)));

	}

}
