package com.marhashoft.requerimentoapi.repository;

import com.marhashoft.requerimentoapi.model.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PessoaRepository extends JpaRepository<Pessoa, Long> {
}
