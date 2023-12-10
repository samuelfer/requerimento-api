package com.marhashoft.requerimentoapi.enums;

import java.util.HashMap;
import java.util.Map;

public enum TipoArquivoEnum {

    FOTO_VEREADOR("Foto_vereador"),
    ARQ_ASSINATURA_VEREADOR("Arquivo_assinatura_vereador"),
    LOGO_REQUERIMENTO("Logo_requerimento");

    private String nome;

    private static final Map<String, TipoArquivoEnum> tipoArquivoPorNome = new HashMap<>();

    static {
        for (TipoArquivoEnum tipo : TipoArquivoEnum.values()) {
            tipoArquivoPorNome.put(tipo.getNome(), tipo);
        }
    }

    TipoArquivoEnum(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public static TipoArquivoEnum recuperarTipoPorNome(String valor) {
        return tipoArquivoPorNome.get(valor);
    }
}
