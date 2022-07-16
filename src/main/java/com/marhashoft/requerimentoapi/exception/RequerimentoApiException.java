package com.marhashoft.requerimentoapi.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class RequerimentoApiException extends RuntimeException  {

    private static final long serialVersionUID = 1L;

    private HttpStatus status;
    private String mensagem;

    public RequerimentoApiException(HttpStatus status, String mensagem) {
        super();
        this.status = status;
        this.mensagem = mensagem;
    }

    public RequerimentoApiException(HttpStatus status, String mensagem, String mensagemN) {
        super();
        this.status = status;
        this.mensagem = mensagem;
        this.mensagem = mensagemN;
    }
}
