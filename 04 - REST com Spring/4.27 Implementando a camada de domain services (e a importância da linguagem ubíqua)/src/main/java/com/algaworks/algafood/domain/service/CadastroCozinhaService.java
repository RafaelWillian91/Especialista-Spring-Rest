package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class CadastroCozinhaService {

    @Autowired
    private CozinhaRepository cozinhaRepository;

    public Cozinha salvar(Cozinha cozinha){
        //poderia ter regras de negócio aki
        return cozinhaRepository.salvar(cozinha);
    }

    public void excluir (Long cozinhaId) {
        try {
            cozinhaRepository.remover(cozinhaId);

        }catch (EmptyResultDataAccessException e){
            //Exception de negocio. que informa que a entidade nao foi encontrada
            throw new EntidadeNaoEncontradaException(
                    String.format("Não existe um cadastro de cozinha com código %d", cozinhaId)
            );

        }catch (DataIntegrityViolationException e){
            //Exception de negocio. que informa que a entidade esta em uso
                throw new EntidadeEmUsoException(
                        String.format("Cozinha de código %d não pode ser removida, pois está em uso", cozinhaId)
                );
            }
        }

}
