package com.marhashoft.requerimentoapi.repository;

import com.marhashoft.requerimentoapi.model.Assessor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AssessorRepository extends JpaRepository<Assessor, Long> {
    Optional<Assessor> findByNome(String nome);
}
