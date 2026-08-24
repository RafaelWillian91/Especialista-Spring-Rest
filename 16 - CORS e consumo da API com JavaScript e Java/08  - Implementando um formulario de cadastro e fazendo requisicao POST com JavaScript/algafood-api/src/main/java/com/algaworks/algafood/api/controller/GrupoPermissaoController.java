package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.PermissaoAssembler;
import com.algaworks.algafood.api.model.dtos.PermissaoDTO;
import com.algaworks.algafood.domain.model.Grupo;
import com.algaworks.algafood.domain.model.Permissao;
import com.algaworks.algafood.domain.repository.GrupoRepository;
import com.algaworks.algafood.domain.repository.PermissaoRepository;
import com.algaworks.algafood.domain.service.CadastroGrupoService;
import com.algaworks.algafood.domain.service.CadastroPermissaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("grupos/{grupoId}/permissoes")
public class GrupoPermissaoController {

    @Autowired
    private PermissaoRepository permissaoRepository;
    @Autowired
    private PermissaoAssembler permissaoAssembler;
    @Autowired
    private GrupoRepository grupoRepository;

    @Autowired
    private CadastroGrupoService cadastroGrupoService;

    @Autowired
    private CadastroPermissaoService permissaoService;
    @GetMapping
    public List<PermissaoDTO> listar (@PathVariable Long grupoId){

        Grupo grupo = cadastroGrupoService.buscarOuFalhar(grupoId);
        Set<Permissao> permissoes = grupo.getPermissoes();

        return permissaoAssembler.toCollectionModel(permissoes);
    }

    @DeleteMapping("/{permissaoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void desassociarPermissao(@PathVariable Long grupoId, @PathVariable Long permissaoId){

        cadastroGrupoService.desassociarGrupoService(grupoId, permissaoId);

    }

    @PutMapping("/{permissaoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void associarPermissao(@PathVariable Long grupoId, @PathVariable Long permissaoId){

        cadastroGrupoService.associarPermissao(grupoId, permissaoId);

    }

}
