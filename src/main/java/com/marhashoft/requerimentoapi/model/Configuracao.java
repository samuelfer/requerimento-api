package com.marhashoft.requerimentoapi.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "configuracao")
public class Configuracao {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "configuracao_seq")
    private Integer id;

    @NotEmpty
    @Size(min = 5, message = "Nome precisa ter no mínimo 5 caracteres")
    private String nome;

    private String logo;

    private String logoDocumento;

    @NotEmpty
    @Size(min = 5, message = "Texto padrão do requerimento precisa ter no mínimo 5 caracteres")
    @Size(max = 1500, message = "Texto padrão do requerimento precisa ter no máximo 1500 caracteres")
    private String textoPadraoRequerimento;

    @NotEmpty
    @Size(min = 5, message = "Texto padrão do ofício precisa ter no mínimo 5 caracteres")
    @Size(max = 1500, message = "Texto padrão do ofício precisa ter no máximo 1500 caracteres")
    private String textoPadraoOficio;
}
