package com.marhashoft.requerimentoapi.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GestaoDTO {

    private Long id;

    @NotNull
    private boolean ativa;

    private LocalDate dataInicio;
    private LocalDate dataFim;
}
