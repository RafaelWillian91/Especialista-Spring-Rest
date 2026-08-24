package com.algaworks.algafood.api.model.input;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class SenhaUsuarioInput {

    @NotBlank
    private String senha;

    @NotBlank
    private String novaSenha;



}
