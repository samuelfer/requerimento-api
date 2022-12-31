package com.marhashoft.requerimentoapi.service;

import com.marhashoft.requerimentoapi.exception.DataIntegrationViolationApiException;
import com.marhashoft.requerimentoapi.model.Assessor;
import com.marhashoft.requerimentoapi.repository.AssessorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AssessorService {

    @Autowired
    AssessorRepository assessorRepository;

    public Assessor findByIdOuErro(Long id) {
        return assessorRepository.findById(id).orElseThrow(() -> new RuntimeException("Assessor não encontrado com id " + id));
    }

    public List<Assessor> listarTodos() {
        return assessorRepository.findAll(Sort.by(Sort.Direction.DESC, "nome"));
    }

    public Assessor salvar(Assessor assessor) {
        assessorJaCadastrado(assessor);
        assessor.setAtivo(true);
        return assessorRepository.save(assessor);
    }

    public Assessor atualizar(Assessor assessor) {
        assessorJaCadastrado(assessor);
        return assessorRepository.save(assessor);
    }

    private void assessorJaCadastrado(Assessor assessor) {
        Optional<Assessor> assessorExists = assessorRepository.findByNome(assessor.getNome());

        if (assessorExists.isPresent() && !assessorExists.get().getId().equals(assessor.getId())) {
            throw new DataIntegrationViolationApiException("O assessor  "
                    + assessor.getNome() + " já foi cadastrado no sistema!");
        }
    }
}
