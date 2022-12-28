package com.marhashoft.requerimentoapi.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
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

    private Pessoa vereador;

    private boolean ativo;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataInicio;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataFim;
}
