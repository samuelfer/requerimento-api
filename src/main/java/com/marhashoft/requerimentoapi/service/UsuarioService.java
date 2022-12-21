package com.marhashoft.requerimentoapi.service;

import com.marhashoft.requerimentoapi.model.Usuario;
import com.marhashoft.requerimentoapi.repository.UsuarioRepository;
import com.marhashoft.requerimentoapi.usuario.UsuarioLogado;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    UsuarioRepository usuarioRepository;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    public Usuario findByIdOuErro(Long id) {
        return usuarioRepository.findById(id).orElseThrow(() -> new RuntimeException("Usuário não encontrado com id " + id));
    }

    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    public Usuario create(Usuario usuario) {
        usuario.setId(null);
        usuario.setAtivo(true);
        usuario.setSenha(bCryptPasswordEncoder.encode(usuario.getSenha()));
        validaPorEmail(usuario);
        return usuarioRepository.save(usuario);
    }

    public Usuario update(Long id, Usuario usuario) {
        usuario.setId(id);
        Usuario oldUsuario = findByIdOuErro(id);

        if(!usuario.getSenha().equals(oldUsuario.getSenha())) {
            oldUsuario.setSenha(bCryptPasswordEncoder.encode(usuario.getSenha()));
        }

        validaPorEmail(usuario);

        return usuarioRepository.save(oldUsuario);
    }

    private void validaPorEmail(Usuario usuarioParam) {
        Optional<Usuario> usuario = usuarioRepository.findByEmail(usuarioParam.getEmail());

        if (usuario.isPresent() && usuario.get().getId() != usuarioParam.getId()) {
            throw new DataIntegrityViolationException("Email já cadastrado no sistema!");
        }
    }

    public Long countUsuario() {
        return usuarioRepository.count();
    }

    public void inativar(Long id, Usuario usuario) {
        findByIdOuErro(id);
        usuario.setAtivo(false);
    }

    public Usuario getusuarioLogadoOuErro() throws RuntimeException {
        String usuarioLogado = getUsuarioLogado();

        if (usuarioLogado == null ) {
            throw new RuntimeException("Usuário logado não encontrado.");
        }
        Optional<Usuario> usuario = usuarioRepository.findByEmail(usuarioLogado);
        if (!usuario.isPresent() ) {
            throw new RuntimeException("Usuário não encontrado.");
        }

        return usuario.get();
    }

    public String getUsuarioLogado() {
        return  UsuarioLogado.getUsuarioContexto().toString();
    }
}
