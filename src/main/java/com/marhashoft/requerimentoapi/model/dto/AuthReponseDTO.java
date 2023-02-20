package com.marhashoft.requerimentoapi.model.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class AuthReponseDTO {

    private Long id;
    private String username;
    private List<String> authorities = new ArrayList<String>();
    private String tokenType = "Bearer ";
    private String accessToken;

    public AuthReponseDTO() {

    }

    public AuthReponseDTO(Long id, String username, List<String> authorities, String accessToken) {
        this.id = id;
        this.username = username;
        this.authorities = authorities;
        this.accessToken = accessToken;
    }
}
