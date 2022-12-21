package com.marhashoft.requerimentoapi.usuario;


import com.marhashoft.requerimentoapi.model.Usuario;
import org.springframework.security.authentication.AuthenticationTrustResolverImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;


public class UsuarioLogado extends Usuario {

    public static Object getUsuarioContexto() {
        return getAuthentication();
    }

    public static Object getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
