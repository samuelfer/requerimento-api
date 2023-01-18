package com.marhashoft.requerimentoapi.repository;

import com.marhashoft.requerimentoapi.model.Cargo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CargoRepository extends JpaRepository<Cargo, Long> {

    Optional<Cargo> findByDescricao(String descricao);
}
