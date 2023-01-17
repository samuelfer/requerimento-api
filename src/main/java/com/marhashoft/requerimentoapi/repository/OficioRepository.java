package com.marhashoft.requerimentoapi.repository;

import com.marhashoft.requerimentoapi.model.Oficio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OficioRepository extends JpaRepository<Oficio, Long> {

    Optional<Oficio> findFirstByNumero(String numero);
}
