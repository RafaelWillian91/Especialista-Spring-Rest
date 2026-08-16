create table foto_produto (
    produto_id bigint not null,
    nome_arquivo varchar(150) not null,
    descricao varchar(150),
    content_type varchar(80) not null,
    tamanho int not null,

    primary key (produto_id),
    constraint fk_foto_poduto_produto foreign key (produto_id) references produto (id)
)   ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;