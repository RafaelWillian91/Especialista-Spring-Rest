package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.model.StatusPedido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FluxoPedidoService {

    @Autowired
    private EmissaoPedidoService emissaoPedidoService;
    @Transactional
    public void confirmar(Long pedidoId){
        Pedido pedido = emissaoPedidoService.buscarOuFalhar(pedidoId);

        if(!pedido.getStatus().equals(StatusPedido.CRIADO)){
            throw new NegocioException(String.format("Status do pedido %s não pode ser alterado de %s " +
                    "para %s", pedido.getId(), pedido.getStatus(), StatusPedido.CRIADO));
        }
    }

}
