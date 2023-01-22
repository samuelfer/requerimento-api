package com.marhashoft.requerimentoapi.util;

import org.apache.commons.io.FilenameUtils;

public class ArquivoUtil {

    public static String transformeNomeArquivo(String nomeArquivo) {
        if (nomeArquivo.isEmpty()) {
            return "";
        }
        return StringUtil.substituiEspacosPorUnderline(StringUtil.removerAcentos(nomeArquivo));
    }

    private static String removeExtension(String nomeArquivo) {
        int lastIndex = nomeArquivo.lastIndexOf('.');
        if (lastIndex != -1) {
            nomeArquivo = nomeArquivo.substring(0, lastIndex);
        }
        return nomeArquivo;
    }

    public static String getExtensionArquivo(String nomeArquivo) {
        return FilenameUtils.getExtension(nomeArquivo);
    }
}
