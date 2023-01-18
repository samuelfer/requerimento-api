package com.marhashoft.requerimentoapi.repository;

import com.marhashoft.requerimentoapi.model.Requerimento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RequerimentoRepository extends JpaRepository<Requerimento, Long> {

    Optional<Requerimento> findFirstByNumero(String numero);
}
