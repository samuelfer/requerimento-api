package com.marhashoft.requerimentoapi.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Entity
@Table(name = "pronome_tratamento")
public class PronomeTratamento {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pronome_tratamento_seq")
    private Integer id;

    @NotBlank(message = "O campo descrição é obrigatória")
    private String descricao;

    private boolean ativo;
}
