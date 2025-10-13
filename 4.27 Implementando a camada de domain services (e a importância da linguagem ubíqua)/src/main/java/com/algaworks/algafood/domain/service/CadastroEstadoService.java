package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.EstadoRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CadastroEstadoService {

    @Autowired
    private EstadoRepository estadoRepository;


    public Estado adicionarEstado(Estado estado){
        return estadoRepository.salvar(estado);
    }

    public Estado atualizarEstado(Estado estado, Long id){

            Estado estado1 = estadoRepository.buscar(id);

            if(estado1 != null){
                BeanUtils.copyProperties(estado, estado1, "id");
                Estado estadoAtualizado = estadoRepository.salvar(estado);
            }

            if(estado1 == null){
            throw new EntidadeNaoEncontradaException(
                    String.format("O %d não foi encontrado ", id)
            );
        }

        return estado1;

    }

        public Estado deletar(Long id){

            Estado estado =  estadoRepository.buscar(id);
            if(estado != null) {
                estadoRepository.remover(estado);
            } else if (estado == null) {
                throw new EntidadeNaoEncontradaException(
                        String.format("O Estado de código %d não existe no banco", id)
                );
            }

            return estado;
        }













}
