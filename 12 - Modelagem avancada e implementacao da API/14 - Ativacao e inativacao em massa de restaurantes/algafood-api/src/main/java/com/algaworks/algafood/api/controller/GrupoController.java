package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.GrupoAssembler;
import com.algaworks.algafood.api.assembler.GrupoDisassembler;
import com.algaworks.algafood.api.model.dtos.GrupoDTO;
import com.algaworks.algafood.api.model.input.GrupoInput;
import com.algaworks.algafood.domain.model.Grupo;
import com.algaworks.algafood.domain.repository.GrupoRepository;
import com.algaworks.algafood.domain.service.CadastroGrupoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/grupos")
public class GrupoController {

    @Autowired
    private GrupoRepository grupoRepository;

    @Autowired
    private GrupoAssembler grupoAssembler;
    @Autowired
    private GrupoDisassembler grupoDisassembler;
    @Autowired
    private CadastroGrupoService cadastroGrupoService;
    @GetMapping
    public List<GrupoDTO> listar(){

        return grupoAssembler.toCollectionModel(grupoRepository.findAll());
    }

    @GetMapping({"/{grupoid}"})
    public GrupoDTO buscar(@PathVariable Long grupoid){
        Grupo grupo = cadastroGrupoService.buscarOuFalhar(grupoid);
        return grupoAssembler.toModel(grupo);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GrupoDTO criarGrupo(@RequestBody @Valid GrupoInput grupoInput){
        Grupo grupo = grupoDisassembler.toModel(grupoInput);

        Grupo novoGrupo = cadastroGrupoService.salvar(grupo);

        return grupoAssembler.toModel(novoGrupo);
    }

    @PutMapping("/{grupoId}")
    public GrupoDTO atualizar (@PathVariable Long grupoId, @RequestBody @Valid GrupoInput grupoInput){

        Grupo grupoBd = cadastroGrupoService.buscarOuFalhar(grupoId);

        grupoDisassembler.toCopy(grupoInput, grupoBd);

        Grupo grupoAtualizado = cadastroGrupoService.salvar(grupoBd);

        return grupoAssembler.toModel(grupoAtualizado);

    }

    @DeleteMapping("/{grupoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluir (@PathVariable Long grupoId){

        cadastroGrupoService.excluirGrupo(grupoId);
    }

}
