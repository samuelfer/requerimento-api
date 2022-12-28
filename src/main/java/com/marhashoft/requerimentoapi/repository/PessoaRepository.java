package com.marhashoft.requerimentoapi.repository;

import com.marhashoft.requerimentoapi.model.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PessoaRepository extends JpaRepository<Pessoa, Long> {

    List<Pessoa> findByAtivoTrueAndTipoPessoaIdOrderByNome(int tipoPessoaId);

    Long countByTipoPessoaId(Long tipoPessoaId);
}
