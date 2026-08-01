package com.algaworks.algafood.api.model.dtos;

import com.algaworks.algafood.domain.model.Restaurante;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@Setter
public class ProdutoDTO {

    private Long id;
    private String nome;

    private String descricao;

    private BigDecimal preco;

    private Boolean ativo;
}
