
    create table estado (
       id bigint not null auto_increment,
        nome varchar(255) not null,
        primary key (id)
    ) engine=InnoDB;

    create table foto_produto (
       id bigint not null auto_increment,
        content_type varchar(255),
        descricao varchar(255),
        nome varchar(255),
        tamanho bigint,
        primary key (id)
    ) engine=InnoDB;

    create table grupo (
       id bigint not null auto_increment,
        nome varchar(255),
        primary key (id)
    ) engine=InnoDB;

    create table grupo_permissao (
       grupo_id bigint not null,
        permissao_id bigint not null
    ) engine=InnoDB;

    create table produto (
       id bigint not null auto_increment,
        ativo bit,
        descricao varchar(255),
        nome varchar(255),
        preco decimal(19,2),
        restaurante_id bigint,
        primary key (id)
    ) engine=InnoDB;

    create table restaurante_forma_pagamento (
       restaurante_id bigint not null,
        forma_pagamento_id bigint not null
    ) engine=InnoDB;

    create table tb_cidade (
       id bigint not null auto_increment,
        nome varchar(255),
        estado_id bigint,
        primary key (id)
    ) engine=InnoDB;

    create table tb_cozinha (
       id bigint not null auto_increment,
        nome varchar(255),
        primary key (id)
    ) engine=InnoDB;

    create table tb_forma_pagamento (
       id bigint not null auto_increment,
        descricao varchar(255),
        primary key (id)
    ) engine=InnoDB;

    create table tb_permissao (
       id bigint not null auto_increment,
        descricao varchar(255),
        nome varchar(255),
        primary key (id)
    ) engine=InnoDB;

    create table tb_restaurante (
       id bigint not null auto_increment,
        data_atualizacao datetime not null,
        data_cadastro datetime not null,
        endereco_bairro varchar(255),
        endereco_cep varchar(255),
        endereco_complemento varchar(255),
        endereco_logradouro varchar(255),
        endereco_numero varchar(255),
        nome varchar(255),
        taxa_frete decimal(19,2) not null,
        cozinha_id bigint not null,
        endereco_cidade_id bigint,
        primary key (id)
    ) engine=InnoDB;

    create table usuario (
       id bigint not null auto_increment,
        data_cadastro datetime(6) not null,
        email varchar(255),
        nome varchar(255),
        senha varchar(255),
        primary key (id)
    ) engine=InnoDB;

    create table usuario_grupo (
       usuario_id bigint not null,
        grupo_id bigint not null
    ) engine=InnoDB;

    alter table grupo_permissao 
       add constraint FKp0syycx497qf03cd3axwt07h7 
       foreign key (permissao_id) 
       references tb_permissao (id);

    alter table grupo_permissao 
       add constraint FKta4si8vh3f4jo3bsslvkscc2m 
       foreign key (grupo_id) 
       references grupo (id);

    alter table produto 
       add constraint FKafid5j2wa5r7xtjxpbdulk6r0 
       foreign key (restaurante_id) 
       references tb_restaurante (id);

    alter table restaurante_forma_pagamento 
       add constraint FKvmcyfbxboylw9gcoxooanja8 
       foreign key (forma_pagamento_id) 
       references tb_forma_pagamento (id);

    alter table restaurante_forma_pagamento 
       add constraint FKi24qvgjh60batfb1qm9tty4yq 
       foreign key (restaurante_id) 
       references tb_restaurante (id);

    alter table tb_cidade 
       add constraint FK5otjnlcgpnici4c6y16ve441d 
       foreign key (estado_id) 
       references estado (id);

    alter table tb_restaurante 
       add constraint FKpsus3qnrv5sxgm95m8epaievn 
       foreign key (cozinha_id) 
       references tb_cozinha (id);

    alter table tb_restaurante 
       add constraint FKp5f7gcswjq064qro5pfjoj49r 
       foreign key (endereco_cidade_id) 
       references tb_cidade (id);

    alter table usuario_grupo 
       add constraint FKk30suuy31cq5u36m9am4om9ju 
       foreign key (grupo_id) 
       references grupo (id);

    alter table usuario_grupo 
       add constraint FKdofo9es0esuiahyw2q467crxw 
       foreign key (usuario_id) 
       references usuario (id);

    create table estado (
       id bigint not null auto_increment,
        nome varchar(255) not null,
        primary key (id)
    ) engine=InnoDB;

    create table foto_produto (
       id bigint not null auto_increment,
        content_type varchar(255),
        descricao varchar(255),
        nome varchar(255),
        tamanho bigint,
        primary key (id)
    ) engine=InnoDB;

    create table grupo (
       id bigint not null auto_increment,
        nome varchar(255),
        primary key (id)
    ) engine=InnoDB;

    create table grupo_permissao (
       grupo_id bigint not null,
        permissao_id bigint not null
    ) engine=InnoDB;

    create table produto (
       id bigint not null auto_increment,
        ativo bit,
        descricao varchar(255),
        nome varchar(255),
        preco decimal(19,2),
        restaurante_id bigint,
        primary key (id)
    ) engine=InnoDB;

    create table restaurante_forma_pagamento (
       restaurante_id bigint not null,
        forma_pagamento_id bigint not null
    ) engine=InnoDB;

    create table tb_cidade (
       id bigint not null auto_increment,
        nome varchar(255),
        estado_id bigint,
        primary key (id)
    ) engine=InnoDB;

    create table tb_cozinha (
       id bigint not null auto_increment,
        nome varchar(255),
        primary key (id)
    ) engine=InnoDB;

    create table tb_forma_pagamento (
       id bigint not null auto_increment,
        descricao varchar(255),
        primary key (id)
    ) engine=InnoDB;

    create table tb_permissao (
       id bigint not null auto_increment,
        descricao varchar(255),
        nome varchar(255),
        primary key (id)
    ) engine=InnoDB;

    create table tb_restaurante (
       id bigint not null auto_increment,
        data_atualizacao datetime not null,
        data_cadastro datetime not null,
        endereco_bairro varchar(255),
        endereco_cep varchar(255),
        endereco_complemento varchar(255),
        endereco_logradouro varchar(255),
        endereco_numero varchar(255),
        nome varchar(255),
        taxa_frete decimal(19,2) not null,
        cozinha_id bigint not null,
        endereco_cidade_id bigint,
        primary key (id)
    ) engine=InnoDB;

    create table usuario (
       id bigint not null auto_increment,
        data_cadastro datetime(6) not null,
        email varchar(255),
        nome varchar(255),
        senha varchar(255),
        primary key (id)
    ) engine=InnoDB;

    create table usuario_grupo (
       usuario_id bigint not null,
        grupo_id bigint not null
    ) engine=InnoDB;

    alter table grupo_permissao 
       add constraint FKp0syycx497qf03cd3axwt07h7 
       foreign key (permissao_id) 
       references tb_permissao (id);

    alter table grupo_permissao 
       add constraint FKta4si8vh3f4jo3bsslvkscc2m 
       foreign key (grupo_id) 
       references grupo (id);

    alter table produto 
       add constraint FKafid5j2wa5r7xtjxpbdulk6r0 
       foreign key (restaurante_id) 
       references tb_restaurante (id);

    alter table restaurante_forma_pagamento 
       add constraint FKvmcyfbxboylw9gcoxooanja8 
       foreign key (forma_pagamento_id) 
       references tb_forma_pagamento (id);

    alter table restaurante_forma_pagamento 
       add constraint FKi24qvgjh60batfb1qm9tty4yq 
       foreign key (restaurante_id) 
       references tb_restaurante (id);

    alter table tb_cidade 
       add constraint FK5otjnlcgpnici4c6y16ve441d 
       foreign key (estado_id) 
       references estado (id);

    alter table tb_restaurante 
       add constraint FKpsus3qnrv5sxgm95m8epaievn 
       foreign key (cozinha_id) 
       references tb_cozinha (id);

    alter table tb_restaurante 
       add constraint FKp5f7gcswjq064qro5pfjoj49r 
       foreign key (endereco_cidade_id) 
       references tb_cidade (id);

    alter table usuario_grupo 
       add constraint FKk30suuy31cq5u36m9am4om9ju 
       foreign key (grupo_id) 
       references grupo (id);

    alter table usuario_grupo 
       add constraint FKdofo9es0esuiahyw2q467crxw 
       foreign key (usuario_id) 
       references usuario (id);

    create table estado (
       id bigint not null auto_increment,
        nome varchar(255) not null,
        primary key (id)
    ) engine=InnoDB;

    create table foto_produto (
       id bigint not null auto_increment,
        content_type varchar(255),
        descricao varchar(255),
        nome varchar(255),
        tamanho bigint,
        primary key (id)
    ) engine=InnoDB;

    create table grupo (
       id bigint not null auto_increment,
        nome varchar(255),
        primary key (id)
    ) engine=InnoDB;

    create table grupo_permissao (
       grupo_id bigint not null,
        permissao_id bigint not null
    ) engine=InnoDB;

    create table produto (
       id bigint not null auto_increment,
        ativo bit,
        descricao varchar(255),
        nome varchar(255),
        preco decimal(19,2),
        restaurante_id bigint,
        primary key (id)
    ) engine=InnoDB;

    create table restaurante_forma_pagamento (
       restaurante_id bigint not null,
        forma_pagamento_id bigint not null
    ) engine=InnoDB;

    create table tb_cidade (
       id bigint not null auto_increment,
        nome varchar(255),
        estado_id bigint,
        primary key (id)
    ) engine=InnoDB;

    create table tb_cozinha (
       id bigint not null auto_increment,
        nome varchar(255),
        primary key (id)
    ) engine=InnoDB;

    create table tb_forma_pagamento (
       id bigint not null auto_increment,
        descricao varchar(255),
        primary key (id)
    ) engine=InnoDB;

    create table tb_permissao (
       id bigint not null auto_increment,
        descricao varchar(255),
        nome varchar(255),
        primary key (id)
    ) engine=InnoDB;

    create table tb_restaurante (
       id bigint not null auto_increment,
        data_atualizacao datetime not null,
        data_cadastro datetime not null,
        endereco_bairro varchar(255),
        endereco_cep varchar(255),
        endereco_complemento varchar(255),
        endereco_logradouro varchar(255),
        endereco_numero varchar(255),
        nome varchar(255),
        taxa_frete decimal(19,2) not null,
        cozinha_id bigint not null,
        endereco_cidade_id bigint,
        primary key (id)
    ) engine=InnoDB;

    create table usuario (
       id bigint not null auto_increment,
        data_cadastro datetime(6) not null,
        email varchar(255),
        nome varchar(255),
        senha varchar(255),
        primary key (id)
    ) engine=InnoDB;

    create table usuario_grupo (
       usuario_id bigint not null,
        grupo_id bigint not null
    ) engine=InnoDB;

    alter table grupo_permissao 
       add constraint FKp0syycx497qf03cd3axwt07h7 
       foreign key (permissao_id) 
       references tb_permissao (id);

    alter table grupo_permissao 
       add constraint FKta4si8vh3f4jo3bsslvkscc2m 
       foreign key (grupo_id) 
       references grupo (id);

    alter table produto 
       add constraint FKafid5j2wa5r7xtjxpbdulk6r0 
       foreign key (restaurante_id) 
       references tb_restaurante (id);

    alter table restaurante_forma_pagamento 
       add constraint FKvmcyfbxboylw9gcoxooanja8 
       foreign key (forma_pagamento_id) 
       references tb_forma_pagamento (id);

    alter table restaurante_forma_pagamento 
       add constraint FKi24qvgjh60batfb1qm9tty4yq 
       foreign key (restaurante_id) 
       references tb_restaurante (id);

    alter table tb_cidade 
       add constraint FK5otjnlcgpnici4c6y16ve441d 
       foreign key (estado_id) 
       references estado (id);

    alter table tb_restaurante 
       add constraint FKpsus3qnrv5sxgm95m8epaievn 
       foreign key (cozinha_id) 
       references tb_cozinha (id);

    alter table tb_restaurante 
       add constraint FKp5f7gcswjq064qro5pfjoj49r 
       foreign key (endereco_cidade_id) 
       references tb_cidade (id);

    alter table usuario_grupo 
       add constraint FKk30suuy31cq5u36m9am4om9ju 
       foreign key (grupo_id) 
       references grupo (id);

    alter table usuario_grupo 
       add constraint FKdofo9es0esuiahyw2q467crxw 
       foreign key (usuario_id) 
       references usuario (id);
