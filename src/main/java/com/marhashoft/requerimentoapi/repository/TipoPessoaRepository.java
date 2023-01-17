package com.marhashoft.requerimentoapi.repository;

import com.marhashoft.requerimentoapi.model.TipoPessoa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TipoPessoaRepository extends JpaRepository<TipoPessoa, Long> {

    List<TipoPessoa> findByAtivoTrueOrderByDescricao();
}
