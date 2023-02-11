package com.marhashoft.requerimentoapi.service;

import com.marhashoft.requerimentoapi.exception.DataIntegrationViolationApiException;
import com.marhashoft.requerimentoapi.model.Assessor;
import com.marhashoft.requerimentoapi.model.Usuario;
import com.marhashoft.requerimentoapi.model.dto.AssessorDTO;
import com.marhashoft.requerimentoapi.repository.AssessorRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AssessorService {

    @Autowired
    AssessorRepository assessorRepository;
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    private UsuarioService usuarioService;

    public Assessor findByIdOuErro(Long id) {
        return assessorRepository.findById(id).orElseThrow(() -> new RuntimeException("Assessor não encontrado com id " + id));
    }

    public List<Assessor> listarTodos() {
        return assessorRepository.findAll(Sort.by(Sort.Direction.DESC, "nome"));
    }

    public Assessor salvar(AssessorDTO assessorDTO) {
        Assessor assessor = modelMapper.map(assessorDTO, Assessor.class);
        assessorJaCadastrado(assessorDTO);
        assessor.setAtivo(true);
        Assessor assessorSalvo = assessorRepository.save(assessor);

        if (assessorDTO.isUsuarioSistema()) {
            usuarioService.create(mapearAssessorToUsuario(assessorSalvo, assessorDTO.getSenha()));
        }
        return assessorSalvo;
    }

    public Assessor atualizar(AssessorDTO assessorDTO) {
        assessorJaCadastrado(assessorDTO);
        Assessor assessor = modelMapper.map(assessorDTO, Assessor.class);
        return assessorRepository.save(assessor);
    }

    private void assessorJaCadastrado(AssessorDTO assessorDTO) {
        Optional<Assessor> assessorExists = assessorRepository.findByNome(assessorDTO.getNome());

        if (assessorExists.isPresent() && !assessorExists.get().getId().equals(assessorDTO.getId())) {
            throw new DataIntegrationViolationApiException("O assessor  "
                    + assessorDTO.getNome() + " já foi cadastrado no sistema!");
        }
    }

    private Usuario mapearAssessorToUsuario(Assessor assessor, String senha) {
        Usuario novoUsuario = new Usuario();
        novoUsuario.setPessoaId(assessor.getId());
        novoUsuario.setNome(assessor.getNome());
        novoUsuario.setUsername(assessor.getEmail());
        novoUsuario.setTipoPessoa(assessor.getTipoPessoa());
        novoUsuario.setSenha(senha);
        return novoUsuario;
    }
}
