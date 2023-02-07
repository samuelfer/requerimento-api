package com.marhashoft.requerimentoapi.service;

import com.marhashoft.requerimentoapi.model.Usuario;
import com.marhashoft.requerimentoapi.repository.UsuarioRepository;
import com.marhashoft.requerimentoapi.security.UsuarioSpringSecurity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Optional<Usuario> usuario = usuarioRepository.findByUsername(email);

        if (usuario.isPresent()) {
            return new UsuarioSpringSecurity(usuario.get().getId(), usuario.get().getNome(), usuario.get().getUsername(),
                    usuario.get().getSenha(), usuario.get().getRoles());
        }
        throw new UsernameNotFoundException(email);
    }
}
