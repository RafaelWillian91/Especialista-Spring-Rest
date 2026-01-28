package com.algaworks.algafood.domain.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Embeddable // classe incorporavel. ele é uma parte de outra entidade e ele vai fazer parte da tabela da entidade
public class Endereco {

    @Column(name = "endereco_cep")
    private String cep;

    @Column(name = "endereco_logradouro")
    private String logradouro;

    @Column(name = "endereco_numero")
    private String numero;

    @Column(name = "endereco_bairro")
    private String bairro;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "endereco_cidade_id")
    private Cidade cidade;

    @Column(name = "endereco_complemento")
    private String complemento;

}
