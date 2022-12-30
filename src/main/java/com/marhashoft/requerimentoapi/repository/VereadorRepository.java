package com.marhashoft.requerimentoapi.repository;

import com.marhashoft.requerimentoapi.model.Vereador;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VereadorRepository extends JpaRepository<Vereador, Long> {
}
