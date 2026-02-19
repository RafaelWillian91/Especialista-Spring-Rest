package com.algaworks.algafood.domain.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.*;

@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Restaurante {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	private Long id;

	@Column(nullable = false)
	private String nome;

	//nao aceita null, Se o banco de dados já existe (criado manualmente ou por migrations como Flyway/Liquibase)
	//, o Hibernate não vai recriar a tabela.Nesse caso, nullable = false não tem efeito direto no banco, mas ainda serve como metadado
	@Column(name = "taxa_frete", nullable = false)
	private BigDecimal taxaFrete;

	@ManyToOne//Muitos restaurantes (Classe) possuem uma cozinha
	@JoinColumn(name = "cozinha_codigo", nullable = false)//para mudar o nome da caluna que é criada do banco para mapeamento. Tambem ultil quando o banco é legado.
	private Cozinha cozinha;//Um restuarante possui uma cozinha

	@ManyToMany
	private List<FormaPagamento> formaPagamento;
	
}
