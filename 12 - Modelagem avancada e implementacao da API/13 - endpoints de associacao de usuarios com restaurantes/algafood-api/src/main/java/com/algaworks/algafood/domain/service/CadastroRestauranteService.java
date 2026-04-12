package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.exception.RestauranteNaoEncontradoException;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CadastroRestauranteService {

	@Autowired
	private RestauranteRepository restauranteRepository;
	
	@Autowired
	private CadastroCozinhaService cadastroCozinha;

	@Autowired
	private CadastroCidadeService cidadeService;

	@Autowired
	private CadastroFormaPagamentoService cadastroFormaPagamentoService;

	@Autowired
	private CadastroUsuarioService cadastroUsuarioService;

	@Transactional
	public Restaurante salvar(Restaurante restaurante) {
		Long cozinhaId = restaurante.getCozinha().getId();

		Cozinha cozinha = cadastroCozinha.buscarOuFalhar(cozinhaId);
		Cidade cidade =   cidadeService.buscarOuFalhar(restaurante.getEndereco().getCidade().getId());

		restaurante.setCozinha(cozinha);
		restaurante.getEndereco().setCidade(cidade);

		return restauranteRepository.save(restaurante);
	}

	@Transactional
	public void ativar (Long restauranteId){
		Restaurante restaurante = buscarOuFalhar(restauranteId);

		restaurante.ativar();
	}

	@Transactional
	public void inativar (Long restauranteId){
		Restaurante restaurante = buscarOuFalhar(restauranteId);

		restaurante.inativar();
	}

	@Transactional
	public void desassociarFormaPagamento(Long restauranteId, Long formapagamentoId){

		Restaurante restaurante = buscarOuFalhar(restauranteId);
		FormaPagamento formaPagamento = cadastroFormaPagamentoService.buscarOuFalhar(formapagamentoId);
		restaurante.removerFormaPagamento(formaPagamento);
	}

	@Transactional
	public void associarFormaPagamento(Long restauranteId, Long formapagamentoId){

		Restaurante restaurante = buscarOuFalhar(restauranteId);
		FormaPagamento formaPagamento = cadastroFormaPagamentoService.buscarOuFalhar(formapagamentoId);
		restaurante.adicionarFormaPagamento(formaPagamento);
	}

	@Transactional
	public void abrirRestaurante(Long restauranteId){
		Restaurante restaurante = buscarOuFalhar(restauranteId);
		restaurante.abrirRestaurante();
	}

	@Transactional
	public void fecharRestaurante(Long restauranteId){
		Restaurante restaurante = buscarOuFalhar(restauranteId);
		restaurante.fecharRestaurante();
	}
	
	public Restaurante buscarOuFalhar(Long restauranteId) {

		return restauranteRepository.findById(restauranteId)
			.orElseThrow(() -> new RestauranteNaoEncontradoException(restauranteId));
	}

	@Transactional
	public void desassociarResponsavel(Long restauranteId, Long usuarioId) {
		Restaurante restaurante = buscarOuFalhar(restauranteId);
		Usuario usuario = cadastroUsuarioService.buscarOuFalhar(usuarioId);

		restaurante.removerResponsavel(usuario);
	}

	@Transactional
	public void associarResponsavel(Long restauranteId, Long usuarioId) {
		Restaurante restaurante = buscarOuFalhar(restauranteId);
		Usuario usuario = cadastroUsuarioService.buscarOuFalhar(usuarioId);

		restaurante.adicionarResponsavel(usuario);
	}
	
}
