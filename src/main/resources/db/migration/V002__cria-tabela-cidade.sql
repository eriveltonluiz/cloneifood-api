create table cidade(
	id bigint primary key auto_increment,
    nome_cidade varchar(80) not null,
	nome_estado varchar(80) not null
)engine=InnoDB;