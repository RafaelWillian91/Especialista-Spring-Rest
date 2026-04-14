create table restaurante_usuario_responsavel (
    restaurante_id bigint not null,
    usuario_id bigint not null,

    primary key (restaurante_id, usuario_id),

    constraint fk_rur_restuarante
    foreign key (restaurante_id) references restaurante(id),

    constraint fk_rur_usuario
    FOREIGN KEY (usuario_id) REFERENCES usuario(id)

)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;