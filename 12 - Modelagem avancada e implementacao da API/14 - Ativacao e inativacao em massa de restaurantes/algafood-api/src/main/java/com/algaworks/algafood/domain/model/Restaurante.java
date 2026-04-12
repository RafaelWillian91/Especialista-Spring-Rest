package com.algaworks.algafood.domain.model;

import com.algaworks.algafood.core.validation.ValorZeroIncluiDescricao;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@ValorZeroIncluiDescricao(valorField = "taxaFrete", descricaoField = "nome", descricaoObrigatoria = "Frete Grátis")
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
	@CreationTimestamp
	@Column(nullable = false, columnDefinition = "datetime")
	private OffsetDateTime dataCadastro;
	@UpdateTimestamp
	@Column(nullable = false, columnDefinition = "datetime")
	private OffsetDateTime dataAtualizacao;

	private Boolean aberto = Boolean.FALSE;

	private Boolean ativo = Boolean.TRUE;
	@ManyToMany
	@JoinTable(name = "restaurante_forma_pagamento",
			joinColumns = @JoinColumn(name = "restaurante_id"),
			inverseJoinColumns = @JoinColumn(name = "forma_pagamento_id"))
	private Set<FormaPagamento> formasPagamento = new HashSet<>();

	@OneToMany(mappedBy = "restaurante")
	private List<Produto> produtos = new ArrayList<>();

	@ManyToMany
	@JoinTable(name = "restaurante_usuario_responsavel",
	       joinColumns = @JoinColumn(name = "restaurante_id"),
	       inverseJoinColumns = @JoinColumn(name = "usuario_id"))
	private Set<Usuario> usuariosResponsaveis = new HashSet<>();

	public void ativar(){
		this.setAtivo(true);
	}
	public void inativar(){
		this.setAtivo(false);
	}

	public void abrirRestaurante(){
		this.setAberto(true);
	}

	public void fecharRestaurante(){
		this.setAberto(false);
	}
	public boolean adicionarFormaPagamento(FormaPagamento formaPagamento){

		return formasPagamento.add(formaPagamento);
	}

	public boolean removerFormaPagamento(FormaPagamento formaPagamento){

		return formasPagamento.remove(formaPagamento);
	}

	public boolean adicionarResponsavel(Usuario usuario){

		return getUsuariosResponsaveis().add(usuario);
	}

	public boolean removerResponsavel(Usuario usuario){

		return getUsuariosResponsaveis().remove(usuario);
	}

}
