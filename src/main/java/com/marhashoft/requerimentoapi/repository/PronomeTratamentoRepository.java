package com.marhashoft.requerimentoapi.repository;

import com.marhashoft.requerimentoapi.model.PronomeTratamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PronomeTratamentoRepository extends JpaRepository<PronomeTratamento, Integer> {

    Optional<PronomeTratamento> findByDescricao(String descricao);
}
