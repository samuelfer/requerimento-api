package com.marhashoft.requerimentoapi;

public enum CargoEnum {

    AGENTE_ADMINISTRATIVO("Agente administrativo", 1L),
    PORTEIRO("Porteiro(a)", 2L),
    SERVICOS_GERAIS("Serviços gerais", 3L),
    MOTORISTA("Motorista", 52L),
    PRIMEIRO_SECRETARIO("Primeiro secretário", 102L),
    VEREADOR("Vereador", 152L);


    private String nome;
    private Long id;

    CargoEnum(String nome, Long id) {
        this.nome = nome;
        this.id = id;
    }

    public String getNome() {
        return nome;
    }
    public Long getId() {
        return id;
    }
}
