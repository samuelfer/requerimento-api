package com.marhashoft.requerimentoapi.model.dto;

import com.marhashoft.requerimentoapi.model.TipoPessoa;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AssessorDTO {

    private Long id;

    @NotBlank(message = "O campo nome é obrigatório")
    private String nome;

    @Valid
    private VereadorDTO vereador;

    private boolean ativo;

    private TipoPessoa tipoPessoa;
}
