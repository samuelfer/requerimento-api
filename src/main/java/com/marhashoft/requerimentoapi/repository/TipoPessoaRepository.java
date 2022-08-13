package com.marhashoft.requerimentoapi.repository;

import com.marhashoft.requerimentoapi.model.TipoPessoa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TipoPessoaRepository extends JpaRepository<TipoPessoa, Long> {

    Optional<TipoPessoa> findByDescricao(String descricao);
}
