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
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
        public MappingJacksonValue listar(@RequestParam(required = false) String campos){
                List<Pedido> pedidoList = pedidoRepository.findAll();
                List<PedidoResumoDTO> pedidoResumoDTOS = pedidoResumoModelAssembler.toCollectionModel(pedidoList);

                MappingJacksonValue pedidosWrapper = new MappingJacksonValue(pedidoResumoDTOS);
                SimpleFilterProvider filterProvider = new SimpleFilterProvider();

                pedidosWrapper.setFilters(filterProvider);
                filterProvider.addFilter("pedidoFilter", SimpleBeanPropertyFilter.serializeAll());

                if(StringUtils.isNotBlank(campos)){

                        Set<String> camposArray = Arrays.stream(campos.split(","))
                                        .map(String::trim)
                                         .filter(c -> !c.isEmpty())
                                .collect(Collectors.toSet());

                        filterProvider.addFilter("pedidoFilter", SimpleBeanPropertyFilter.filterOutAllExcept(
                                camposArray
                        ));
                }

                return pedidosWrapper;

        }

//        @GetMapping
//        public List<PedidoResumoDTO> listar(){
//                List<Pedido> pedidoList = pedidoRepository.findAll();
//                return pedidoResumoModelAssembler.toCollectionModel(pedidoList);
//        }

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
