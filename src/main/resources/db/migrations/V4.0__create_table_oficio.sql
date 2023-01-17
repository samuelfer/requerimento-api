CREATE TABLE IF NOT EXISTS public.requerimento (
	id int8 NOT NULL,
	assunto text NOT NULL,
	data_oficio timestamp NULL,
	numero varchar(255) NULL,
	pessoa_id int8 NOT NULL,
	usuario_id int8 NOT NULL,
	destinatario  text NOT NULL,
	CONSTRAINT requerimento_pkey PRIMARY KEY (id)
);

ALTER TABLE public.pessoa OWNER TO postgres;
ALTER TABLE public.requerimento ADD CONSTRAINT oficio_pessoa_id_fk FOREIGN KEY (pessoa_id) REFERENCES public.pessoa(id);
ALTER TABLE public.requerimento ADD CONSTRAINT oficio_usuario FOREIGN KEY (usuario_id) REFERENCES public.usuario(id);
