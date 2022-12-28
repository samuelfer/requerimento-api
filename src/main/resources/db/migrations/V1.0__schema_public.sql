CREATE SCHEMA IF NOT EXISTS public;

ALTER SCHEMA auditoria OWNER TO postgres;

SET default_tablespace = '';

CREATE TABLE IF NOT EXISTS public.gerador_numero (
	ano int4 NOT NULL,
	numero int4 NULL,
	CONSTRAINT gerador_numero_pkey PRIMARY KEY (ano)
);

ALTER TABLE public.gerador_numero OWNER TO postgres;
--##############################################


CREATE TABLE IF NOT EXISTS public.tipo_pessoa (
	id int8 NOT NULL,
	descricao varchar(255) NULL,
	ativo bool NULL,
	CONSTRAINT tipo_pessoa_pkey PRIMARY KEY (id)
);

ALTER TABLE public.tipo_pessoa OWNER TO postgres;
--##############################################

CREATE TABLE IF NOT EXISTS public.usuario (
	id int8 NOT NULL,
	ativo bool NOT NULL,
	data_criacao date NULL,
	email varchar(255) NULL,
	nome varchar(255) NOT NULL,
	senha varchar(255) NULL,
	CONSTRAINT email_unique_usuario UNIQUE (email),
	CONSTRAINT usuario_pkey PRIMARY KEY (id)
);

ALTER TABLE public.usuario OWNER TO postgres;
--##############################################

CREATE TABLE IF NOT EXISTS public.perfis (
	usuario_id int8 NOT NULL,
	perfis int4 NULL
);

ALTER TABLE public.perfis OWNER TO postgres;
ALTER TABLE public.perfis ADD CONSTRAINT perfis_usuario FOREIGN KEY (usuario_id) REFERENCES public.usuario(id);
--##############################################

CREATE TABLE IF NOT EXISTS public.pessoa (
	id int8 NOT NULL,
	ativo bool NOT NULL,
	cargo varchar(255) NULL,
	nome varchar(255) NOT NULL,
	tipo_pessoa_id int8 NOT NULL
	CONSTRAINT pessoa_pkey PRIMARY KEY (id)
);

ALTER TABLE public.pessoa OWNER TO postgres;
ALTER TABLE public.pessoa ADD CONSTRAINT pessoa_tipo_pessoa FOREIGN KEY (tipo_pessoa_id) REFERENCES public.tipo_pessoa(id);
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

ALTER TABLE public.pessoa OWNER TO postgres;
ALTER TABLE public.requerimento ADD CONSTRAINT pessoa_id_fk FOREIGN KEY (pessoa_id) REFERENCES public.pessoa(id);
ALTER TABLE public.requerimento ADD CONSTRAINT requerimento_usuario FOREIGN KEY (usuario_id) REFERENCES public.usuario(id);
