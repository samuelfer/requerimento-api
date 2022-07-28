package com.marhashoft.requerimentoapi.service;

import com.marhashoft.requerimentoapi.model.Usuario;
import com.marhashoft.requerimentoapi.model.dto.UsuarioDTO;
import com.marhashoft.requerimentoapi.repository.UsuarioRepository;
import org.modelmapper.ModelMapper;
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

    public Usuario create(UsuarioDTO usuarioDTO) {
        usuarioDTO.setId(null);
        usuarioDTO.setSenha(bCryptPasswordEncoder.encode(usuarioDTO.getSenha()));
        validaPorEmail(usuarioDTO);
        ModelMapper modelMapper = new ModelMapper();
        Usuario usuario = modelMapper.map(usuarioDTO, Usuario.class);
        return usuarioRepository.save(usuario);
    }

    public UsuarioDTO update(Long id, UsuarioDTO usuarioDTO) {
        usuarioDTO.setId(id);
        Usuario oldUsuario = findByIdOuErro(id);

        if(!usuarioDTO.getSenha().equals(oldUsuario.getSenha())) {
            oldUsuario.setSenha(bCryptPasswordEncoder.encode(usuarioDTO.getSenha()));
        }

        validaPorEmail(usuarioDTO);

        Usuario usuarioSalvo = usuarioRepository.save(oldUsuario);

        ModelMapper modelMapper = new ModelMapper();
        UsuarioDTO usuarioDTOResponse = modelMapper.map(usuarioSalvo, UsuarioDTO.class);
        return usuarioDTOResponse;
    }

    private void validaPorEmail(UsuarioDTO usuarioDTO) {
        Optional<Usuario> usuario = usuarioRepository.findByEmail(usuarioDTO.getEmail());

        if (usuario.isPresent() && usuario.get().getId() != usuarioDTO.getId()) {
            throw new DataIntegrityViolationException("Email já cadastrado no sistema!");
        }
    }
}
