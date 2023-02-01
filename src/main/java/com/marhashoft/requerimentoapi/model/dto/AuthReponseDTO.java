package com.marhashoft.requerimentoapi.model.dto;

import lombok.Data;

@Data
public class AuthReponseDTO {

    private String accessToken;
    private String tokenType = "Bearer ";

    public AuthReponseDTO(String accessToken) {
        this.accessToken = accessToken;
    }
}
