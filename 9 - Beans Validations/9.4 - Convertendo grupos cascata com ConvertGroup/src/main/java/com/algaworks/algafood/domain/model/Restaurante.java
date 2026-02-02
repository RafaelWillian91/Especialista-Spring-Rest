package com.algaworks.algafood.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.*;
import javax.validation.groups.ConvertGroup;
import javax.validation.groups.Default;

import com.algaworks.algafood.Groups;
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

	//@NotNull
	//@NotEmpty
	@NotBlank
	@Column(nullable = false)
	private String nome;

	//@DecimalMin("0")
	@PositiveOrZero
	@NotNull
	@Column(name = "taxa_frete", nullable = false)
	private BigDecimal taxaFrete;

	//@JsonIgnore
	//Existe um proxy criado quando é Lazy, senao fica null (cozinha) e essa propriedade sem o JsonIgnore esta sendo acessada, mas nao tem nada
	//@JsonIgnoreProperties("hibernateLazyInitializer") //Entao ignoramos a propriedade da classe proxy de cozinha do Hibernate chamada hibernateLazyInitializer
	@Valid
	@ConvertGroup(from = Default.class, to = Groups.CozinhaId.class)
	@ManyToOne//(fetch = FetchType.LAZY)
	@JoinColumn(name = "cozinha_id", nullable = false)
	@NotNull
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

	@ManyToMany
	//@JsonIgnore
	@JoinTable(name = "restaurante_forma_pagamento", //A entidade que declara o @JoinTable é a dona da relação
	joinColumns = @JoinColumn(name = "restaurante_id"),
	inverseJoinColumns = @JoinColumn(name = "forma_pagamento_id"))
	private List<FormaPagamento> formasPagamentos = new ArrayList<>();

	@JsonIgnore
	@OneToMany(mappedBy = "restaurante")
	private List <Produto> produtos = new ArrayList<>();

}
