package com.marhashoft.requerimentoapi.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@PrimaryKeyJoinColumn(name="pessoaId")
public class Assessor extends Pessoa {

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "vereador_id", referencedColumnName = "id")
    private Pessoa vereador;
}
