package com.algaworks.algafood.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

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
	@JsonIgnore
	private Endereco endereco;

	@JsonIgnore
	@CreationTimestamp //deve ser atribuida a data e hora atual. Anotações são do Hibernate (não do JPA puro)
	@Column(nullable = false, columnDefinition = "datetime") // Como estamos gerando o BC automaticamente essa propriedade cria um campo notNull
	private LocalDateTime dataCadastro;

	@JsonIgnore
	@UpdateTimestamp // sempre que a entidade for atualizada // Anotações são do Hibernate (não do JPA puro)
	@Column(nullable = false, columnDefinition = "datetime")
	private LocalDateTime dataAtualizacao;

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
