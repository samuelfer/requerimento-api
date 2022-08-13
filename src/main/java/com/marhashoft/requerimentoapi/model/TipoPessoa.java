package com.marhashoft.requerimentoapi.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "tipo_pessoa")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TipoPessoa {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tipo_pessoa_seq")
    private Long id;

    @Column(nullable = false)
    private String descricao;

    private boolean ativo;
}
