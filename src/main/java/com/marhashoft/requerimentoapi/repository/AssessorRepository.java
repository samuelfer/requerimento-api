package com.marhashoft.requerimentoapi.repository;

import com.marhashoft.requerimentoapi.model.Assessor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AssessorRepository extends JpaRepository<Assessor, Long> {
    Optional<Assessor> findByNome(String nome);
}
