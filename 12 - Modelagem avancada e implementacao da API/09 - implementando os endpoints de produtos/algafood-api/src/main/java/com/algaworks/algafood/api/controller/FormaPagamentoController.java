package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.FormaPagamentoDisassembler;
import com.algaworks.algafood.api.assembler.FormaPagamentoModelAssembler;
import com.algaworks.algafood.api.model.dtos.FormaPagamentoDTO;
import com.algaworks.algafood.api.model.input.FormaPagamentoInput;
import com.algaworks.algafood.domain.model.FormaPagamento;
import com.algaworks.algafood.domain.repository.FormaPagamentoRepository;
import com.algaworks.algafood.domain.service.CadastroFormaPagamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/formas-pagamento")
public class FormaPagamentoController {

    @Autowired
    private FormaPagamentoRepository formaPagamentoRepository;
    @Autowired
    private CadastroFormaPagamentoService cadastroFormaPagamentoService;
    @Autowired
    private FormaPagamentoModelAssembler formaPagamentoModelAssembler;
    @Autowired
    private FormaPagamentoDisassembler formaPagamentoDisassembler;

    @GetMapping
    public List<FormaPagamentoDTO> listar(){
        List<FormaPagamento> todasFormasPagamentos = formaPagamentoRepository.findAll();
        return formaPagamentoModelAssembler.toCollectionModel(todasFormasPagamentos);
    }

    @GetMapping({"/{formasPagamentoId}"})
    public FormaPagamentoDTO buscar (@PathVariable Long formasPagamentoId){
        FormaPagamento formaPagamento = formaPagamentoRepository.getReferenceById(formasPagamentoId);
        return formaPagamentoModelAssembler.toModel(formaPagamento);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FormaPagamentoDTO adicionar (@RequestBody @Valid FormaPagamentoInput formaPagamentoInput){

       FormaPagamento formaPagamento = formaPagamentoDisassembler.toDomainObject(formaPagamentoInput);
       cadastroFormaPagamentoService.salvar(formaPagamento);

       return formaPagamentoModelAssembler.toModel(formaPagamento);
    }

    @PutMapping("/{formaPagamentoId}")
    public FormaPagamentoDTO atualizarFormaPagamento (@RequestBody FormaPagamentoInput formaPagamentoInput,
    @PathVariable Long formaPagamentoId){

        FormaPagamento formaPagamento = cadastroFormaPagamentoService.buscarOuFalhar(formaPagamentoId);

        formaPagamentoDisassembler.copyToDomainObject(formaPagamentoInput, formaPagamento);
        cadastroFormaPagamentoService.salvar(formaPagamento);

        return formaPagamentoModelAssembler.toModel(formaPagamento);
    }


    @DeleteMapping({"/{formaPagamentoId}"})
    public void remover (@PathVariable Long formaPagamentoId){
        cadastroFormaPagamentoService.excluir(formaPagamentoId);
    }


}
