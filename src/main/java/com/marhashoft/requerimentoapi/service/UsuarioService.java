package com.marhashoft.requerimentoapi.service;

import com.marhashoft.requerimentoapi.model.Role;
import com.marhashoft.requerimentoapi.model.Usuario;
import com.marhashoft.requerimentoapi.model.UsuarioPerfil;
import com.marhashoft.requerimentoapi.model.dto.IUsuarioResponse;
import com.marhashoft.requerimentoapi.repository.RoleRepository;
import com.marhashoft.requerimentoapi.repository.UsuarioRepository;
import com.marhashoft.requerimentoapi.usuario.UsuarioLogado;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Usuario findByIdOuErro(Long id) {
        return usuarioRepository.findById(id).orElseThrow(() -> new RuntimeException("Usuário não encontrado com id " + id));
    }

    public IUsuarioResponse findById(Long id) {
        Optional<IUsuarioResponse> usuario = usuarioRepository.findUsuarioById(id);
        if (usuario.isEmpty()) {
            throw new RuntimeException("Usuário não encontrado com id " + id);
        }
        return usuario.get();
    }

    public List<IUsuarioResponse> findAll() {
        return usuarioRepository.findAllUsuarios();
    }

    public Usuario create(Usuario usuario) {
        usuario.setId(null);
        usuario.setAtivo(true);
        usuario.setUsername(usuario.getUsername());
        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        validaPorEmail(usuario);

        Role roles = roleRepository.findByNome("ROLE_USER").get();
        usuario.setRoles(Collections.singletonList(roles));

        return usuarioRepository.save(usuario);
    }

    public Usuario update(Usuario usuario) {
        usuario.setId(usuario.getId());
        Usuario oldUsuario = findByIdOuErro(usuario.getId());

        validaPorEmail(usuario);

        if(usuario.getSenha() != null && !usuario.getSenha().equals(oldUsuario.getSenha())) {
            oldUsuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        }

        return usuarioRepository.save(oldUsuario);
    }

    private void validaPorEmail(Usuario usuarioParam) {
        Optional<Usuario> usuario = usuarioRepository.findByUsername(usuarioParam.getUsername());

        if (usuario.isPresent() && !Objects.equals(usuario.get().getId(), usuarioParam.getId())) {
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
        Optional<Usuario> usuario = usuarioRepository.findByUsername(usuarioLogado);
        if (!usuario.isPresent() ) {
            throw new RuntimeException("Usuário não encontrado.");
        }

        return usuario.get();
    }

    public String getUsuarioLogado() {
        return  UsuarioLogado.getUsuarioContexto().toString();
    }

    public boolean usuarioLogadoIsAssessor() {
        return getusuarioLogadoOuErro().getTipoPessoa().getId().equals(TipoPessoaService.TIPO_ASSESSOR);
    }

    public boolean usuarioLogadoIsVereador() {
        return getusuarioLogadoOuErro().getTipoPessoa().getId().equals(TipoPessoaService.TIPO_VEREADOR);
    }

    public Usuario associarPerfil(UsuarioPerfil usuarioPerfil) {
        Usuario usuario = findByIdOuErro(usuarioPerfil.getUsuario().getId());

        if (!usuarioPerfil.getRolesId().isEmpty()) {
            List<Role> roles = new ArrayList<>();
            usuarioPerfil.getRolesId().forEach(roleId -> {
                Optional<Role> role = roleRepository.findById(roleId);

                if (role.isPresent()) {
                    roles.add(role.get());
                }
            });
            usuario.setRoles(roles);
        }

        return usuarioRepository.save(usuario);
    }
}
