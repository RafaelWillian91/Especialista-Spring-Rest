package com.algaworks.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.CidadeRepository;
import com.algaworks.algafood.domain.repository.EstadoRepository;

@Service
public class CadastroCidadeService {

	private static final String MSG_CIDADE_NAO_ENCONTRADA = "Não existe um cadastro de Cidade com código %d";
	public static final String NÃO_PODE_SER_REMOVIDA_POIS_ESTÁ_EM_USO = "Cidade de código %d não pode ser removida, pois está em uso";
	@Autowired
	private CidadeRepository cidadeRepository;

	@Autowired
	private CadastroEstadoService cadastroEstadoService;
	
	public Cidade salvar(Cidade cidade) {
		Long estadoId = cidade.getEstado().getId();

		Estado estado = cadastroEstadoService.buscarOuFalhar(estadoId);
		
		cidade.setEstado(estado);
		
		return cidadeRepository.save(cidade);
	}
	
	public void excluir(Long cidadeId) {
		try {
			cidadeRepository.deleteById(cidadeId);
			
		} catch (EmptyResultDataAccessException e) {
			throw new EntidadeNaoEncontradaException(
				String.format(MSG_CIDADE_NAO_ENCONTRADA, cidadeId));
		
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(
				String.format(NÃO_PODE_SER_REMOVIDA_POIS_ESTÁ_EM_USO, cidadeId));
		}
	}

	public Cidade buscarOuFalhar (Long cidadeId){
		 return cidadeRepository.findById(cidadeId).orElseThrow(() -> new EntidadeNaoEncontradaException(String.format(
				 MSG_CIDADE_NAO_ENCONTRADA,
				 cidadeId)
		));

	}
	
}
