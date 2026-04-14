package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.PedidoAssembler;
import com.algaworks.algafood.api.model.dtos.PedidoDTO;
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.repository.PedidoRepository;
import com.algaworks.algafood.domain.service.EmissaoPedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pedidos/{pedidosId}")
public class PedidoController {

        @Autowired
        private PedidoAssembler pedidoAssembler;

        @Autowired
        private EmissaoPedidoService emissaoPedidoService;
        @Autowired
        private PedidoRepository pedidoRepository;

        @GetMapping
        public PedidoDTO buscarPedido(@PathVariable Long pedidosId){

                return pedidoAssembler.toModel(pedidoRepository.getReferenceById(pedidosId));
        }

        @GetMapping("/{pedidoId}")
        public PedidoDTO buscar(@PathVariable Long pedidoId) {
                Pedido pedido = emissaoPedidoService.buscarOuFalhar(pedidoId);

                return pedidoAssembler.toModel(pedido);
        }


}
