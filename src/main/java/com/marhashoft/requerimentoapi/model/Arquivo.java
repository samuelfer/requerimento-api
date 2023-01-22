package com.marhashoft.requerimentoapi.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "arquivo")
public class Arquivo {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "arquivo_seq")
    private Long id;
    private String nome;
    private String url;
    private String extension;
    private long size;
    private boolean ativo;
    private String tipoArquivo;//Se eh imagem de vereador, assinatura de vereador, ou outro tipo
}
