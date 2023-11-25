package com.marhashoft.requerimentoapi.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "gestao")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Gestao {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pessoa_seq")
    private Long id;

    @NotNull
    private boolean ativa;


    private LocalDate dataInicio;
    private LocalDate dataFim;
}
