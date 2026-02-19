package com.algaworks.algafood.api.controller;

import java.util.List;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.service.CadastroCozinhaService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;

@RestController
@RequestMapping("/cozinhas")
public class CozinhaController {

	@Autowired
	private CozinhaRepository cozinhaRepository;

	@Autowired
	private CadastroCozinhaService cadastroCozinhaService;

	@GetMapping
	public List<Cozinha> listar() {

		return cozinhaRepository.listar();
	}
	@GetMapping("/{cozinhaId}")
	public ResponseEntity<Cozinha> buscaCozinha(@PathVariable Long cozinhaId) {

		Cozinha cozinha = cozinhaRepository.buscar(cozinhaId);
		if(cozinha != null){
			return ResponseEntity.ok(cozinha);
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		 
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Cozinha adicionarCozinha(@RequestBody Cozinha cozinha){

		return cadastroCozinhaService.salvar(cozinha);
	}


	@PutMapping("/{id}")
	public ResponseEntity<Cozinha> atualizarCozinha(@PathVariable Long id, @RequestBody Cozinha cozinha){

		Cozinha cozinhaAtual = cozinhaRepository.buscar(id);
		//o valor de cozinha mapeada do método deve refletir na cozinha buscada no banco para atualizacao
		//poderia setar valor por valor, mas existe um forma mais prática usando a classe BeanUtils
		//é passado a origem e o destino. O terceiro parametro é ignorado
		if(cozinhaAtual != null) {
			BeanUtils.copyProperties(cozinha, cozinhaAtual, "id");
			cozinhaAtual = cadastroCozinhaService.salvar(cozinhaAtual);
			return ResponseEntity.ok(cozinhaAtual);
		}
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> deletarCozinha(@PathVariable Long id){
		try {
			cadastroCozinhaService.excluir(id);
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

		}catch (EntidadeNaoEncontradaException e){
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getLocalizedMessage());
			//return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

		}catch (EntidadeEmUsoException e){
			return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getLocalizedMessage());
		}
	}
	
}










