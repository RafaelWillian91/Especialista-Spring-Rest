package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.PedidoAssembler;
import com.algaworks.algafood.api.assembler.PedidoDisassembler;
import com.algaworks.algafood.api.assembler.PedidoResumoModelAssembler;
import com.algaworks.algafood.api.model.dtos.PedidoDTO;
import com.algaworks.algafood.api.model.dtos.PedidoResumoDTO;
import com.algaworks.algafood.api.model.input.PedidoInput;
import com.algaworks.algafood.core.data.PageableTranslator;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.repository.PedidoRepository;
import com.algaworks.algafood.domain.filter.PedidoFilter;
import com.algaworks.algafood.domain.service.EmissaoPedidoService;
import com.algaworks.algafood.infrastructure.repository.spec.PedidoSpecs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;


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
        public Page<PedidoResumoDTO> pesquisar(PedidoFilter pedidoFilter,@PageableDefault(size = 10)Pageable pageable){

                pageable = traduzirPageable(pageable);
                Page <Pedido> pedidoPage = pedidoRepository.findAll(PedidoSpecs.usandoFiltro(pedidoFilter), pageable);

                List<PedidoResumoDTO> pedidoResumoDTOS = pedidoResumoModelAssembler.toCollectionModel(pedidoPage.getContent());

                Page<PedidoResumoDTO>  pedidoResumoDTOPage =  new PageImpl<>(pedidoResumoDTOS, pageable, pedidoPage.getTotalElements());

                return pedidoResumoDTOPage;
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

        private Pageable traduzirPageable (Pageable apiPageable){

                var mapeamento = Map.of(
                        "codigo", "codigo",
                        "subtotal", "subtotal",
                        "taxaFrete", "taxaFrete",
                        "valorTotal", "valorTotal",
                        "dataCriacao", "dataCriacao",
                        "restaurante.nome", "restaurante.nome",
                        "restaurante.id", "restaurante.id",
                        "cliente.id", "cliente.id",
                        "cliente.nome", "cliente.nome"
                );

                return PageableTranslator.translate(apiPageable, mapeamento);
        }


}
