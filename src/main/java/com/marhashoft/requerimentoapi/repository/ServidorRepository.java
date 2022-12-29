package com.marhashoft.requerimentoapi.repository;

import com.marhashoft.requerimentoapi.model.Servidor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ServidorRepository extends JpaRepository<Servidor, Long> {

    Optional<Servidor> findByNome(String nome);
}
