package com.marhashoft.requerimentoapi.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CargoDTO {

    private Long id;

    @NotBlank(message = "O campo cargo é obrigatório")
    private String descricao;

    private boolean ativo;
}
