package com.marhashoft.requerimentoapi.enums;

public enum TipoArquivoEnum {

    FOTO_VEREADOR("Foto_vereador"),
    ARQ_ASSINATURA_VEREADOR("Arquivo_assinatura_vereador");

    private String nome;

    TipoArquivoEnum(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }
}
