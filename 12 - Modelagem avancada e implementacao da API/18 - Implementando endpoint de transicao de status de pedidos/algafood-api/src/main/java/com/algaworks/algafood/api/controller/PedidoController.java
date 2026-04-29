package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.PedidoAssembler;
import com.algaworks.algafood.api.assembler.PedidoDisassembler;
import com.algaworks.algafood.api.assembler.PedidoResumoModelAssembler;
import com.algaworks.algafood.api.model.dtos.PedidoDTO;
import com.algaworks.algafood.api.model.dtos.PedidoResumoDTO;
import com.algaworks.algafood.api.model.input.PedidoInput;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.repository.PedidoRepository;
import com.algaworks.algafood.domain.service.EmissaoPedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {
        @Autowired
        private PedidoAssembler pedidoAssembler;
        @Autowired
        private EmissaoPedidoService emissaoPedidoService;
        @Autowired
        private PedidoRepository pedidoRepository;
        @Autowired
        private PedidoResumoModelAssembler pedidoResumoModelAssembler;
        @Autowired
        private PedidoDisassembler pedidoDisassembler;
        @GetMapping
        public List<PedidoResumoDTO> buscarPedido(){
                List<Pedido> pedidoList = pedidoRepository.findAll();
                return pedidoResumoModelAssembler.toCollectionModel(pedidoList);
        }

        @GetMapping("/{codigoPedido}")
        public PedidoDTO buscar(@PathVariable String codigoPedido) {
                Pedido pedido = emissaoPedidoService.buscarOuFalhar(codigoPedido);
                return pedidoAssembler.toModel(pedido);
        }
        @PostMapping
        public PedidoDTO criarPedido (@RequestBody @Valid PedidoInput pedidoInput){

                try {
                        Pedido novoPedido = pedidoDisassembler.toModel(pedidoInput);

                        // TODO pegar usuário autenticado
                        novoPedido.setCliente(new Usuario());
                        novoPedido.getCliente().setId(1L);

                        novoPedido = emissaoPedidoService.emitir(novoPedido);

                        return pedidoAssembler.toModel(novoPedido);
                } catch (EntidadeNaoEncontradaException e) {
                        throw new NegocioException(e.getMessage(), e);
                }

        }



}
