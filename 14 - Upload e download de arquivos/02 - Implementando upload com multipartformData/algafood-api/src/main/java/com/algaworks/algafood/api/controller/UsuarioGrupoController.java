package com.algaworks.algafood.api.controller;


import com.algaworks.algafood.api.assembler.GrupoAssembler;
import com.algaworks.algafood.api.model.dtos.GrupoDTO;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.service.CadastroGrupoService;
import com.algaworks.algafood.domain.service.CadastroUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios/{usuarioId}/grupos")
public class UsuarioGrupoController {
    @Autowired
    private CadastroUsuarioService cadastroUsuarioService;

    @Autowired
    private GrupoAssembler grupoAssembler;
    @GetMapping
    public List<GrupoDTO> buscarGrupoUsuario(@PathVariable Long usuarioId){

        Usuario usuario = cadastroUsuarioService.buscarOuFalhar(usuarioId);
        return grupoAssembler.toCollectionModel(usuario.getGrupos());
    }

    @DeleteMapping("/{grupoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletarGrupoUsuario (@PathVariable Long usuarioId, @PathVariable Long grupoId){
        cadastroUsuarioService.desassociarGrupo(usuarioId, grupoId);

    }

    @PutMapping("/{grupoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void associarGrupoUsuario (@PathVariable Long usuarioId, @PathVariable Long grupoId){
        cadastroUsuarioService.associarGrupo(usuarioId, grupoId);

    }
}
