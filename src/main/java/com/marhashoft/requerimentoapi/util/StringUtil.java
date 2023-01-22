package com.marhashoft.requerimentoapi.util;

import java.text.Normalizer;

public class StringUtil {

    protected static String removerAcentos(String str) {
        return Normalizer.normalize(str, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
    }

    protected static String substituiEspacosPorUnderline(String str) {
        return str.replaceAll(" ", "_");
    }
}
