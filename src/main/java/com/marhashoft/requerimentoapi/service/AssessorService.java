package com.marhashoft.requerimentoapi.service;

import com.marhashoft.requerimentoapi.model.Assessor;
import com.marhashoft.requerimentoapi.repository.AssessorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

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
        assessor.setAtivo(true);
        return assessorRepository.save(assessor);
    }
}
