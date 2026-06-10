create table cidade(

    id bigInt not null auto_increment,
    nome_cidade varchar(80) not null,
    nome_estado varchar(80) not null,
    primary key (id)

)ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;