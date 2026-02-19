package com.algaworks.algafood.domain.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Restaurante {

	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private String nome;
	
	@Column(name = "taxa_frete", nullable = false)
	private BigDecimal taxaFrete;
	
	@ManyToOne
	@JoinColumn(name = "cozinha_id", nullable = false)
	private Cozinha cozinha;

	@Embedded
	private Endereco endereco;

	@ManyToMany //Uma tabela extra é gerada para representar o relacionamento.
	// 	Isso pode ser invisível no código, mas está lá no banco.
	//Cada vez que você adiciona ou remove algo da lista, o Hibernate faz operações na tabela de junção.
	//Dependendo do tamanho da lista, ele pode gerar muitos DELETE e INSERT automáticos.
	//@ManyToMany é bonito no papel, mas difícil de manter em sistemas grandes.
	//O @ManyToMany não permite atributos extras (ex: data de ativação, prioridade etc).
	//Por isso, em projetos reais, é comum substituir por uma entidade intermediária
	@JsonIgnore
	@JoinTable(name = "restaurante_forma_pagamento", //A entidade que declara o @JoinTable é a dona da relação
	joinColumns = @JoinColumn(name = "restaurante_id"),
	inverseJoinColumns = @JoinColumn(name = "forma_pagamento_id"))
	private List<FormaPagamento> formasPagamentos = new ArrayList<>();
	
}
