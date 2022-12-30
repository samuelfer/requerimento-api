package com.marhashoft.requerimentoapi.repository;

import com.marhashoft.requerimentoapi.model.Vereador;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VereadorRepository extends JpaRepository<Vereador, Long> {
    Optional<Vereador> findByNome(String nome);
}
