package com.marhashoft.requerimentoapi.model.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class ErrorDetalhe {

    private LocalDateTime timesTamp;
    private String mensagem;
    private String detalhe;
}
