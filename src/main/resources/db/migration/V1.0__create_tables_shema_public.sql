CREATE SCHEMA IF NOT EXISTS public;

ALTER SCHEMA public OWNER TO postgres;

CREATE TABLE IF NOT EXISTS public.gerador_numero (
	ano int4 NOT NULL,
	numero int4 NULL,
	CONSTRAINT gerador_numero_pkey PRIMARY KEY (ano)
);

ALTER TABLE public.gerador_numero OWNER TO postgres;

--##############################################

CREATE TABLE public.arquivo (
	id int8 NOT NULL,
	url varchar(255) NULL,
	ativo bool NOT NULL,
	nome varchar(255) NULL,
	"size" int8 NOT NULL,
	tipo varchar(255) NULL,
	"extension" varchar(255) NULL,
	tipo_arquivo varchar(255) NULL,
	CONSTRAINT arquivo_pkey PRIMARY KEY (id)
);

ALTER TABLE public.arquivo OWNER TO postgres;
--##############################################

CREATE TABLE public.cargo (
	id int8 NOT NULL,
	ativo bool NOT NULL,
	descricao varchar(255) NULL,
	CONSTRAINT cargo_pkey PRIMARY KEY (id)
);

ALTER TABLE public.cargo OWNER TO postgres;

--##############################################

CREATE TABLE public.configuracao (
	id int4 NOT NULL,
	logo varchar(255) NULL,
	logo_documento varchar(255) NULL,
	nome text NULL,
	texto_padrao_oficio varchar(1500) NULL,
	texto_padrao_requerimento varchar(1500) NULL,
	CONSTRAINT configuracao_pkey PRIMARY KEY (id)
);

ALTER TABLE public.configuracao OWNER TO postgres;
--##############################################

CREATE TABLE public.pronome_tratamento (
	id int4 NOT NULL,
	ativo bool NOT NULL,
	descricao varchar(255) NULL,
	CONSTRAINT pronome_tratamento_pkey PRIMARY KEY (id)
);

ALTER TABLE public.pronome_tratamento OWNER TO postgres;
--##############################################

CREATE TABLE IF NOT EXISTS public.tipo_pessoa (
	id int8 NOT NULL,
	descricao varchar(255) NULL,
	ativo bool NULL,
	CONSTRAINT tipo_pessoa_pkey PRIMARY KEY (id)
);

ALTER TABLE public.tipo_pessoa OWNER TO postgres;

--##############################################

CREATE TABLE public.gestao (
	id int8 NOT NULL,
	ativo bool NOT NULL,
	data_fim date NULL,
	data_inicio date NULL,
	pessoa_id int8 NULL,
	CONSTRAINT gestao_pkey PRIMARY KEY (id)
);

ALTER TABLE public.gestao OWNER TO postgres;

ALTER TABLE public.gestao ADD CONSTRAINT gestao_pessoa FOREIGN KEY (pessoa_id) REFERENCES public.pessoa(id);

--##############################################

CREATE TABLE IF NOT EXISTS public.usuario (
	id int8 NOT NULL,
	ativo bool NOT NULL,
	data_criacao date NULL,
	email varchar(255) NULL,
	nome varchar(255) NOT NULL,
	senha varchar(255) NULL,
	pessoa_id int8 NULL,
	CONSTRAINT email_unique_usuario UNIQUE (email),
	CONSTRAINT usuario_pkey PRIMARY KEY (id)
);

ALTER TABLE public.usuario OWNER TO postgres;

--##############################################

CREATE TABLE public."role" (
	id int8 NOT NULL,
	nome varchar(255) NULL,
	CONSTRAINT role_pkey PRIMARY KEY (id)
);

ALTER TABLE public."role" OWNER TO postgres;
--##############################################

CREATE TABLE public.usuario_roles (
	usuario_id int8 NOT NULL,
	role_id int8 NOT NULL
);

ALTER TABLE public.usuario_roles OWNER TO postgres;

ALTER TABLE public.usuario_roles ADD CONSTRAINT usuario_roles_role FOREIGN KEY (role_id) REFERENCES public."role"(id);
ALTER TABLE public.usuario_roles ADD CONSTRAINT usuario_roles_usuario FOREIGN KEY (usuario_id) REFERENCES public.usuario(id);

--##############################################

CREATE TABLE IF NOT EXISTS public.pessoa (
	id int8 NOT NULL,
	ativo bool NOT NULL,
	cargo varchar(255) NULL,
	nome varchar(255) NOT NULL,
	tipo_pessoa_id int8 NOT NULL,
	CONSTRAINT pessoa_pkey PRIMARY KEY (id)
);

ALTER TABLE public.pessoa OWNER TO postgres;

ALTER TABLE public.pessoa OWNER TO postgres;
ALTER TABLE public.pessoa ADD CONSTRAINT pessoa_tipo_pessoa FOREIGN KEY (tipo_pessoa_id) REFERENCES public.tipo_pessoa(id);

--##############################################

CREATE TABLE public.pessoa_cargo (
	id int8 NOT NULL,
	ativo bool NOT NULL,
	data_inicio date NULL,
	cargo_id int8 NULL,
	pessoa_id int8 NULL,
	CONSTRAINT pessoa_cargo_pkey PRIMARY KEY (id),
	CONSTRAINT uk_8rmouet372qj2pm911klf0a10 UNIQUE (cargo_id)
);

ALTER TABLE public.pessoa_cargo OWNER TO postgres;

ALTER TABLE public.pessoa_cargo ADD CONSTRAINT pessoa_cargo_cargo FOREIGN KEY (cargo_id) REFERENCES public.cargo(id);
ALTER TABLE public.pessoa_cargo ADD CONSTRAINT pessoa_cargo_pessoa FOREIGN KEY (pessoa_id) REFERENCES public.pessoa(id);

--##############################################

--##############################################

CREATE TABLE public.vereador (
	pessoa_id int8 NOT NULL,
	cargo_id int8 NULL,
	arquivo_assinatura varchar(255) NULL,
	CONSTRAINT vereador_pkey PRIMARY KEY (pessoa_id)
);

ALTER TABLE public.vereador OWNER TO postgres;

ALTER TABLE public.vereador ADD CONSTRAINT vereador_pessoa FOREIGN KEY (pessoa_id) REFERENCES public.pessoa(id);
ALTER TABLE public.vereador ADD CONSTRAINT vereador_pessoa_cargo FOREIGN KEY (cargo_id) REFERENCES public.cargo(id);



--##############################################

CREATE TABLE public.assessor (
	pessoa_id int8 NOT NULL,
	vereador_id int8 NULL,
	CONSTRAINT assessor_pkey PRIMARY KEY (pessoa_id)
);


ALTER TABLE public.assessor OWNER TO postgres;

ALTER TABLE public.assessor ADD CONSTRAINT assessor_vereador FOREIGN KEY (vereador_id) REFERENCES public.vereador(pessoa_id);
ALTER TABLE public.assessor ADD CONSTRAINT assessor_pessoa_vereador FOREIGN KEY (vereador_id) REFERENCES public.pessoa(id);
ALTER TABLE public.assessor ADD CONSTRAINT assessor_pessoa FOREIGN KEY (pessoa_id) REFERENCES public.pessoa(id);
--##############################################

CREATE TABLE IF NOT EXISTS public.requerimento (
	id int8 NOT NULL,
	assunto text NOT NULL,
	data_requerimento timestamp NULL,
	numero varchar(255) NULL,
	pessoa_id int8 NOT NULL,
	usuario_id int8 NOT NULL,
	CONSTRAINT requerimento_pkey PRIMARY KEY (id)
);


ALTER TABLE public.requerimento OWNER TO postgres;

ALTER TABLE public.requerimento ADD CONSTRAINT pessoa_id_fk FOREIGN KEY (pessoa_id) REFERENCES public.pessoa(id);
ALTER TABLE public.requerimento ADD CONSTRAINT requerimento_usuario FOREIGN KEY (usuario_id) REFERENCES public.usuario(id);

--##############################################

CREATE TABLE public.servidor (
	pessoa_id int8 NOT NULL,
	cargo_id int8 NULL,
	CONSTRAINT servidor_pkey PRIMARY KEY (pessoa_id)
);

ALTER TABLE public.servidor OWNER TO postgres;

ALTER TABLE public.servidor ADD CONSTRAINT servidor_pessoa FOREIGN KEY (pessoa_id) REFERENCES public.pessoa(id);
ALTER TABLE public.servidor ADD CONSTRAINT servidor_pessoa_cargo FOREIGN KEY (cargo_id) REFERENCES public.cargo(id);

--##############################################

CREATE TABLE IF NOT EXISTS public.oficio (
	id int8 NOT NULL,
	texto text NOT NULL,
	assunto text NOT NULL,
	data_oficio timestamp NULL,
	numero varchar(255) NULL,
	assinante_id int8 NOT NULL,
	usuario_id int8 NOT NULL,
	destinatario  text NOT NULL,
	CONSTRAINT requerimento_pkey PRIMARY KEY (id)
);

ALTER TABLE public.pessoa OWNER TO postgres;
ALTER TABLE public.oficio ADD CONSTRAINT oficio_pessoa_id_fk FOREIGN KEY (pessoa_id) REFERENCES public.pessoa(id);
ALTER TABLE public.oficio ADD CONSTRAINT oficio_usuario FOREIGN KEY (usuario_id) REFERENCES public.usuario(id);
