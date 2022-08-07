package com.marhashoft.requerimentoapi.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@Setter
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class DataIntegrationViolationApiException extends DataIntegrityViolationException {

    private  static final long serialVersionUID = 1L;

    private String nomeRecurso;

    public DataIntegrationViolationApiException(String nomeRecurso) {
        super(String .format(nomeRecurso));
        this.nomeRecurso = nomeRecurso;
    }
}
