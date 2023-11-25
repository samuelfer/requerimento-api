package com.marhashoft.requerimentoapi.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GestaoDTO {

    private Long id;

    private Boolean ativa;

    private LocalDate dataInicio;

    private LocalDate dataFim;
}
