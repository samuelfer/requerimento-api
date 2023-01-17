package com.marhashoft.requerimentoapi.repository;

import com.marhashoft.requerimentoapi.model.Servidor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ServidorRepository extends JpaRepository<Servidor, Long> {

    Optional<Servidor> findByNome(String nome);
}
