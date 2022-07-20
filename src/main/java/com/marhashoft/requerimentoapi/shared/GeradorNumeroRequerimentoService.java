package com.marhashoft.requerimentoapi.shared;

import com.marhashoft.requerimentoapi.shared.model.GeradorNumero;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class GeradorNumeroRequerimentoService {

    @Autowired
    GeradorNumeroRequerimentoRepository geradorNumeroRepository;

    public String novoNumero() {
        int ano = LocalDate.now().getYear();
        GeradorNumero geradorNumero = getGeradorNumero(ano);
        int numero = incrementarNumero(geradorNumero);
        return formatarNumero(numero, ano);
    }

    /**
     * Formata o número no estilo 00/0000.
     */
    private String formatarNumero(int numero, int ano) {
        return String.format("%02d/%d", numero, ano);
    }

    private GeradorNumero getGeradorNumero(int ano) {
        Optional<GeradorNumero> opt = this.geradorNumeroRepository.findByAno(ano);
        if (opt.isPresent()) {
            return opt.get();
        }
        GeradorNumero gerador = new GeradorNumero(ano, 0);
        return gerador;
    }

    private int incrementarNumero(GeradorNumero gerador) {
        gerador.setNumero(gerador.getNumero() + 1);
        this.geradorNumeroRepository.save(gerador);
        return gerador.getNumero();
    }
}
