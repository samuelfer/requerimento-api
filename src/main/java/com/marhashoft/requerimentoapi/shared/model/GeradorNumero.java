package com.marhashoft.requerimentoapi.shared.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity(name = "gerador_numero")
@Table(name = "gerador_numero")
@AllArgsConstructor
@NoArgsConstructor
public class GeradorNumero {

    @Id
    @Column
    private int ano;

    @Column
    private int numero;
}
