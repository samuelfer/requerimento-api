CREATE TABLE IF NOT EXISTS public.assessor (
	id int8 NOT NULL,
	ativo bool NOT NULL,
	cargo varchar(255) NULL,
	nome varchar(255) NOT NULL,
	tipo_pessoa_id int8 NOT NULL,
	vereador_id int8 NOT NULL
	CONSTRAINT pessoa_pkey PRIMARY KEY (id)
);

ALTER TABLE public.assessor OWNER TO postgres;
ALTER TABLE public.assessor ADD CONSTRAINT pessoa_tipo_pessoa FOREIGN KEY (tipo_pessoa_id) REFERENCES public.tipo_pessoa(id);
