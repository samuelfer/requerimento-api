package com.marhashoft.requerimentoapi.model.dto;

import com.marhashoft.requerimentoapi.model.Cargo;
import com.marhashoft.requerimentoapi.model.TipoPessoa;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VereadorDTO {

    private Long id;

    @NotBlank(message = "O campo nome do vereador é obrigatório")
    private String nome;

    private String email;

    private boolean ativo;

    private TipoPessoa tipoPessoa;

    private Cargo cargo;

    private boolean usuarioSistema;

}
