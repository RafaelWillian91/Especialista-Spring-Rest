package com.algaworks.algafood.api.model.dtos;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Setter
@Getter
public class FormaPagamentoDTO {

    private Long id;
    private String descricao;

}
