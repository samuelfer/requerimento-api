package com.marhashoft.requerimentoapi.model.dto;

import com.marhashoft.requerimentoapi.model.Cargo;
import com.marhashoft.requerimentoapi.model.TipoPessoa;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ServidorDTO {

    private Long id;

    @NotBlank(message = "O campo nome é obrigatório")
    private String nome;

    @Valid
    private Cargo cargo;

    private boolean ativo;

    private TipoPessoa tipoPessoa;
}
