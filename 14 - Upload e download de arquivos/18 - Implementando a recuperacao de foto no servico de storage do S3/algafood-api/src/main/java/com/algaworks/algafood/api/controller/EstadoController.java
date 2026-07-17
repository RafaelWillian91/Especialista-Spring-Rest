package com.algaworks.algafood.api.controller;

import java.util.List;

import javax.validation.Valid;

import com.algaworks.algafood.api.assembler.EstadoAssembler;
import com.algaworks.algafood.api.assembler.EstadoDisassembler;
import com.algaworks.algafood.api.model.dtos.EstadoDTO;
import com.algaworks.algafood.api.model.input.EstadoInput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.EstadoRepository;
import com.algaworks.algafood.domain.service.CadastroEstadoService;

@RestController
@RequestMapping("/estados")
public class EstadoController {

	@Autowired
	private EstadoRepository estadoRepository;
	
	@Autowired
	private CadastroEstadoService cadastroEstado;

	@Autowired
	private EstadoAssembler estadoAssembler;

	@Autowired
	private EstadoDisassembler estadoDisassembler;
	@GetMapping
	public List<EstadoDTO> listar() {

		return estadoAssembler.toCollectionDTO(estadoRepository.findAll());
	}
	
	@GetMapping("/{estadoId}")
	public EstadoDTO buscar(@PathVariable Long estadoId) {

		return estadoAssembler.toModel(cadastroEstado.buscarOuFalhar(estadoId));
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public EstadoDTO adicionar(@RequestBody @Valid EstadoInput estadoInput) {
		Estado estado = estadoDisassembler.toDomainObject(estadoInput);
		cadastroEstado.salvar(estado);
		return estadoAssembler.toModel(estado);
	}
	
	@PutMapping("/{estadoId}")
	public EstadoDTO atualizar(@PathVariable Long estadoId,
			@RequestBody @Valid EstadoInput estadoInput) {

		Estado estadoAtual = cadastroEstado.buscarOuFalhar(estadoId);
		
		estadoDisassembler.copyEstado(estadoInput, estadoAtual);
		cadastroEstado.salvar(estadoAtual);

		return estadoAssembler.toModel(estadoAtual);
	}
	
	@DeleteMapping("/{estadoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void remover(@PathVariable Long estadoId) {

		cadastroEstado.excluir(estadoId);
	}
	
}
