package com.marhashoft.requerimentoapi.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@Setter
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {

    private  static final long serialVersionUID = 1L;

    private String nomeRecurso;
    private String nomeCampo;
    private long valorCampo;

    public ResourceNotFoundException(String nomeRecurso, String nomeCampo, long valorCampo) {
        super(String .format("%s não encontrado com %s : '%s'", nomeRecurso, nomeCampo, valorCampo));
        this.nomeRecurso = nomeRecurso;
        this.nomeCampo = nomeCampo;
        this.valorCampo = valorCampo;
    }
}
