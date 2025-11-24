package com.algaworks.algafood.domain.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.swing.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Usuario {

        @Id
        @EqualsAndHashCode.Include
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(nullable = false)
        private String nome;

        @Column(nullable = false)
        private String email;

        @Column(nullable = false)
        private String senha;

        @CreationTimestamp
        @Column(nullable = false, columnDefinition = "datetime")
        private LocalDateTime dataCriacao;

        @ManyToMany
        @JoinTable(name = "usuario_grupo",
        joinColumns = @JoinColumn (name = "usuario_grupo_id"),
        inverseJoinColumns = @JoinColumn(name = "grupo_usuario_id")
        )
        private Set<Grupo> grupos = new HashSet<>();

}
