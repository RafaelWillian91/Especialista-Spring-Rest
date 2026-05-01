package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.RestauranteAssembler;
import com.algaworks.algafood.api.assembler.RestauranteDisassembler;
import com.algaworks.algafood.api.model.dtos.RestauranteDTO;
import com.algaworks.algafood.api.model.input.RestauranteInput;
import com.algaworks.algafood.api.model.view.RestauranteView;
import com.algaworks.algafood.domain.exception.CidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.CozinhaNaoEncontradaException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.exception.RestauranteNaoEncontradoException;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.validation.SmartValidator;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/restaurantes")
public class RestauranteController {

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private CadastroRestauranteService cadastroRestaurante;

    @Autowired
    private SmartValidator smartValidator;

    @Autowired
    private RestauranteAssembler restauranteAssembler;
    @Autowired
    private RestauranteDisassembler restauranteDisassembler;

    @JsonView(RestauranteView.Resumo.class)
    @GetMapping
    public List<RestauranteDTO> listar() {
        return restauranteAssembler.toCollectionModel(restauranteRepository.findAll());
    }

    @JsonView(RestauranteView.ApenasNomes.class)
    @GetMapping(params = "projecao=nomes")
    public List<RestauranteDTO> listarApenasNomes() {
        return restauranteAssembler.toCollectionModel(restauranteRepository.findAll());
    }


//    @GetMapping
//    public MappingJacksonValue listar(@RequestParam(required = false) String projecao){
//        List<Restaurante> pedidos = restauranteRepository.findAll();
//        List<RestauranteDTO> restauranteDTOS = restauranteAssembler.toCollectionModel(pedidos);
//
//        MappingJacksonValue pedidoWrapper = new MappingJacksonValue(restauranteDTOS);
//
//        pedidoWrapper.setSerializationView(RestauranteView.Resumo.class);
//
//        if ("nomes".equals(projecao)) {
//
//            pedidoWrapper.setSerializationView(RestauranteView.ApenasNomes.class);
//        }else if ("completo".equals(projecao)){
//
//            pedidoWrapper.setSerializationView(null);
//        }
//        return pedidoWrapper;
//    }

//    @GetMapping
//    public List<RestauranteDTO> listar() {
//        return restauranteAssembler.toCollectionModel(restauranteRepository.findAll());
//    }

//    @JsonView(RestauranteView.Resumo.class)
//    @GetMapping(params = "projecao=resumo")
//    public List<RestauranteDTO> listarResumo() {
//        return listar();
//    }
//
//    @JsonView(RestauranteView.ApenasNomes.class)
//    @GetMapping(params = "projecao=apenasNomes")
//    public List<RestauranteDTO> listarApenasNomes() {
//        return listar();
//    }
    @GetMapping("/{restauranteId}")
    public RestauranteDTO buscar(@PathVariable Long restauranteId) {
        Restaurante restaurante = cadastroRestaurante.buscarOuFalhar(restauranteId);

        return restauranteAssembler.toModel(restaurante);
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RestauranteDTO adicionar(@RequestBody @Valid RestauranteInput restauranteInput) {

        try {
            Restaurante restaurante = restauranteDisassembler.toDomainObject(restauranteInput);

            return restauranteAssembler.toModel(cadastroRestaurante.salvar(restaurante));
        } catch (CozinhaNaoEncontradaException | CidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage());
        }
    }

    @PutMapping("/{restauranteId}")
    public RestauranteDTO atualizar(@PathVariable Long restauranteId,
                                    @RequestBody @Valid RestauranteInput restauranteInput) {
        try {
            Restaurante restauranteAtual = cadastroRestaurante.buscarOuFalhar(restauranteId);

            restauranteDisassembler.copyToDomainObject(restauranteInput, restauranteAtual);

            return restauranteAssembler.toModel(cadastroRestaurante.salvar(restauranteAtual));
        } catch (CozinhaNaoEncontradaException | CidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage());
        }
    }

    @PutMapping("/{restauranteId}/ativar")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void ativar(@PathVariable Long restauranteId){

        cadastroRestaurante.ativar(restauranteId);
    }

    @DeleteMapping("/{restauranteId}/ativo")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void inativar(@PathVariable Long restauranteId){

        cadastroRestaurante.inativar(restauranteId);
    }

    @PutMapping("/{restauranteId}/abertura")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void abrirRestaurante(@PathVariable Long restauranteId){
        cadastroRestaurante.abrirRestaurante(restauranteId);
    }
    @PutMapping("/{restauranteId}/fechamento")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void fecharRestaurante(@PathVariable Long restauranteId){
        cadastroRestaurante.fecharRestaurante(restauranteId);
    }


    @PutMapping("/ativacoes")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void ativarMultiplos(@RequestBody List<Long> restauranteIds){
        try {
            cadastroRestaurante.ativar(restauranteIds);
        }catch (RestauranteNaoEncontradoException e){
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @DeleteMapping("/inativacoes")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void inativarMultiplos(@RequestBody List<Long> restauranteIds){
        try {
            cadastroRestaurante.inativar(restauranteIds);
        }catch (RestauranteNaoEncontradoException e){
            throw new NegocioException(e.getMessage(), e);
        }
    }
}
