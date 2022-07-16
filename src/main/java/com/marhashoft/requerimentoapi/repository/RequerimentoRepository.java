package com.marhashoft.requerimentoapi.repository;

import com.marhashoft.requerimentoapi.model.Requerimento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RequerimentoRepository extends JpaRepository<Requerimento, Long> {
}
