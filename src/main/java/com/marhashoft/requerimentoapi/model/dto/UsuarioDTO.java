package com.marhashoft.requerimentoapi.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.marhashoft.requerimentoapi.model.Perfil;
import com.marhashoft.requerimentoapi.model.Usuario;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class UsuarioDTO {

    protected Long id;

    @NotNull(message = "O campo nome é obrigatório")
    protected String nome;

    @NotNull(message = "O campo email é obrigatório")
    protected String username;

    @NotNull(message = "O campo senha é obrigatório")
    protected String senha;

    protected Set<Integer> perfis = new HashSet<>();

    @JsonFormat(pattern = "dd/MM/yyyy")
    protected LocalDate dataCriacao = LocalDate.now();

    protected boolean ativo;

    public UsuarioDTO() {
        super();
        addPerfil(Perfil.USUARIO);
    }

    public UsuarioDTO(UsuarioDTO usuarioObj) {
        super();
        this.id = usuarioObj.getId();
        this.nome = usuarioObj.getNome();
        this.username = usuarioObj.getEmail();
        this.senha = usuarioObj.getSenha();
        this.perfis = usuarioObj.getPerfis().stream().map(x -> x.getCodigo()).collect(Collectors.toSet());
        this.ativo = true;
        this.dataCriacao = usuarioObj.getDataCriacao();
        addPerfil(Perfil.USUARIO);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return username;
    }

    public void setEmail(String email) {
        this.username = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Set<Perfil> getPerfis() {
        return perfis.stream().map(x -> Perfil.toEnum(x)).collect(Collectors.toSet());
    }

    public void addPerfil(Perfil perfil) {
        this.perfis.add(perfil.getCodigo());
    }

    public LocalDate getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDate dataCriacao) {
        this.dataCriacao = dataCriacao;
    }
}
