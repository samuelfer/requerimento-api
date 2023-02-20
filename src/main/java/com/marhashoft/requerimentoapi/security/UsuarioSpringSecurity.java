package com.marhashoft.requerimentoapi.security;

import com.marhashoft.requerimentoapi.model.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class UsuarioSpringSecurity implements UserDetails {

    private Long id;
    private String nome;
    private String username;
    private String senha;
    private Collection<?  extends GrantedAuthority> authorities;

    public UsuarioSpringSecurity(Long id, String nome, String username, String senha, List<Role> perfis) {
        super();
        this.id = id;
        this.nome = nome;
        this.username = username;
        this.senha = senha;
        this.authorities = perfis.stream().map(p -> new SimpleGrantedAuthority(p.getNome())).collect(Collectors.toSet());
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
