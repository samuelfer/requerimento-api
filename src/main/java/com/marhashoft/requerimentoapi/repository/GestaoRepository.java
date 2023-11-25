package com.marhashoft.requerimentoapi.repository;

import com.marhashoft.requerimentoapi.model.Gestao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface GestaoRepository extends JpaRepository<Gestao, Long> {

    Optional<Gestao> findByDataInicioAndDataFim(LocalDate dataInicio, LocalDate dataFim);
}
