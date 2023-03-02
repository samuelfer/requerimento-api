package com.marhashoft.requerimentoapi.service;

import com.marhashoft.requerimentoapi.exception.DataIntegrationViolationApiException;
import com.marhashoft.requerimentoapi.model.Servidor;
import com.marhashoft.requerimentoapi.model.Usuario;
import com.marhashoft.requerimentoapi.model.dto.ServidorDTO;
import com.marhashoft.requerimentoapi.repository.ServidorRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ServidorService {

    @Autowired
    private ServidorRepository servidorRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private UsuarioService usuarioService;

    public Servidor findByIdOuErro(Long id) {
        return servidorRepository.findById(id).orElseThrow(() -> new RuntimeException("Servidor não encontrado com id " + id));
    }

    public List<Servidor> listarTodos() {
        return servidorRepository.findAll(Sort.by(Sort.Direction.DESC, "nome"));
    }

    public Servidor salvar(ServidorDTO servidorDTO) {
        servidorJaCadastrado(servidorDTO);
        servidorDTO.setAtivo(true);
        Servidor servidor = modelMapper.map(servidorDTO, Servidor.class);
        Servidor servidorSalvo = servidorRepository.save(servidor);

        if (servidorDTO.isUsuarioSistema()) {
            if (servidorDTO.getSenha().isEmpty()) {
                throw new RuntimeException("Por favor informe uma senha.");
            }
            usuarioService.create(mapearServidorToUsuario(servidorSalvo, servidorDTO.getSenha()));
        }
        return servidorSalvo;
    }

    public Servidor atualizar(ServidorDTO servidorDTO) {
        servidorJaCadastrado(servidorDTO);
        Servidor servidor = modelMapper.map(servidorDTO, Servidor.class);
        return servidorRepository.save(servidor);
    }

    private void servidorJaCadastrado(ServidorDTO servidorDTO) {
        Optional<Servidor> servidorExists = servidorRepository.findByNome(servidorDTO.getNome());

        if (servidorExists.isPresent() && !servidorExists.get().getId().equals(servidorDTO.getId())) {
            throw new DataIntegrationViolationApiException("O servidor  "
                    + servidorDTO.getNome() + " já foi cadastrado no sistema!");
        }
    }

    private Usuario mapearServidorToUsuario(Servidor servidor, String senha) {
        Usuario novoUsuario = new Usuario();
        novoUsuario.setPessoaId(servidor.getId());
        novoUsuario.setNome(servidor.getNome());
        novoUsuario.setUsername(servidor.getEmail());
        novoUsuario.setTipoPessoa(servidor.getTipoPessoa());
        novoUsuario.setSenha(senha);
        return novoUsuario;
    }
}
