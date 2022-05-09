create table estado(
	id bigint primary key auto_increment,
    nome varchar(80) not null
) engine=InnoDB;

alter table cidade add estado_id bigint not null;

alter table cidade add constraint fk_cidade_estado foreign key(estado_id) references estado(id);

alter table cidade drop column nome_estado;

alter table cidade change nome_cidade nome varchar(80) not null;