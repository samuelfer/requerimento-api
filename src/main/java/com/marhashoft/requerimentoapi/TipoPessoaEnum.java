package com.marhashoft.requerimentoapi;

public enum TipoPessoaEnum {

    TIPO_USUARIO(1L,"Usuário"),
    TIPO_VEREADOR(2L,"Vereador"),
    TIPO_SERVIDOR(3L,"Servidor"),
    TIPO_ASSESSOR(4L,"Assessor"),
    TIPO_ADMIN(5L,"Admin");

    private Long id;
    private String descricao;

    TipoPessoaEnum(Long id, String descricao) {
        this.id = id;
        this.descricao = descricao;
    }

    public Long getId() {
        return id;
    }

    public String getDescricao() {
        return descricao;
    }
}
