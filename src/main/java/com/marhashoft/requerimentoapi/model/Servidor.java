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
public class Servidor extends Pessoa {

    @ManyToOne
    @JoinColumn(name = "cargo_id", foreignKey = @ForeignKey(name = "pessoa_cargo"))
    private Cargo cargo;
}
