package com.marhashoft.requerimentoapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @ManyToOne
    @JoinColumn(name="vereador_id")
    @JsonIgnore
    private Vereador vereador;

    @ManyToOne
    @JoinColumn(name = "cargo_id", foreignKey = @ForeignKey(name = "assessor_cargo"))
    private Cargo cargo;
}
