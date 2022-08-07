package com.marhashoft.requerimentoapi.repository;

import com.marhashoft.requerimentoapi.model.Requerimento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RequerimentoRepository extends JpaRepository<Requerimento, Long> {

    Optional<Requerimento> findFirstByNumero(String numero);
}
