create database amemaisanimais;

create table tab_quem_somos(
	id int not null auto_increment,
	descricao text not null,
	constraint pk_quem_somos primary key(id)
) engine = InnoDB;

create table tab_contato(
	id int not null auto_increment,
	email varchar(100) not null unique,
	telefone varchar(15) not null,
	celular varchar(15) not null,
	constraint pk_contato primary key(id)
) engine = InnoDB;

create table tab_contato_endereco(
	id int not null auto_increment,
	contato int not null unique,
	cep varchar(8) not null,
	logradouro varchar(120) not null,
	numero int not null,
	bairro varchar(100) not null,
	cidade varchar(100) not null,
	uf char(2) not null,
	constraint pk_contato_endereco primary key(id),
	constraint fk_contato_endereco_contato foreign key(contato) references tab_contato(id)
) engine = InnoDB;

create table tab_perfil(
	id int not null auto_increment,
	descricao varchar(60) not null unique,
	constraint pk_perfil primary key(id)
) engine = InnoDB;

insert into tab_perfil(descricao) values('Admin'),('Professor'),('Aluno');

create table tab_usuario(
	id int not null auto_increment,
	nome varchar(150) not null,
	username varchar(50) not null unique,
	senha varchar(120) not null,
	perfil int not null,
	constraint pk_usuario primary key(id),
	constraint fk_usuario_perfil foreign key(perfil) references tab_perfil(id)
) engine = InnoDB;

create table tab_legislacao(
	id int not null auto_increment,
	titulo varchar(120) not null unique,
	descricao text not null,
	constraint pk_legislacao primary key(id)
) engine = InnoDB;

create table tab_dificuldade(
	id int not null auto_increment,
	descricao varchar(40) not null unique,
	pontuacao int not null,
	constraint pk_dificuldade primary key(id)
) engine = InnoDB;

insert into tab_dificuldade (descricao, pontuacao) values ('Fácil', 50), ('Média', 75), ('Difícil', 100);

create table tab_pergunta(
	id int not null auto_increment,
	descricao text not null,
	dificuldade int not null,
	constraint pk_pergunta primary key(id),
	constraint fk_pergunta_dificuldade foreign key(dificuldade) references tab_dificuldade(id)
) engine = InnoDB;

create table tab_resposta(
	id int not null auto_increment,
	descricao text not null,
	correta tinyint not null default 0,
	pergunta int not null,
	constraint pk_respota primary key(id),
	constraint fk_resposta_pergunta foreign key(pergunta) references tab_pergunta(id)
) engine = InnoDB;

create table tab_categoria(
	id int not null auto_increment,
	descricao varchar(60) not null unique,
	constraint pk_categoria primary key(id)
) engine = InnoDB;

insert into tab_categoria(descricao) values ('Culinária'),('Doença'),('Tratamento');

create table tab_dica(
	id int not null auto_increment,
	titulo varchar(255) not null unique,
	descricao text not null,
	constraint pk_dica primary key(id)
) engine = InnoDB;

create table tab_dica_imagem(
	id int not null auto_increment,
	nome varchar(100) not null unique,
	titulo varchar(255) not null unique,
	descricao text not null,
	dica int not null,
	constraint pk_dica_imagem primary key(id),
	constraint fk_dica_imagem_dica foreign key(dica) references tab_dica(id)
) engine = InnoDB;

create table tab_categoria_dica(
	id int not null auto_increment,
	categoria int not null,
	dica int not null,
	constraint pk_categoria_dica primary key(id),
	constraint fk_categoria foreign key(categoria) references tab_categoria(id),
	constraint fk_dica foreign key(dica) references tab_dica(id),
	constraint un_categoria_dica unique(categoria,dica)
) engine = InnoDB;


