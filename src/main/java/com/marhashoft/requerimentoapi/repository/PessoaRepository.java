package com.marhashoft.requerimentoapi.repository;

import com.marhashoft.requerimentoapi.model.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, Long> {

    List<Pessoa> findByAtivoTrueAndTipoPessoaIdOrderByNome(Long tipoPessoaId);

    Long countByTipoPessoaId(Long tipoPessoaId);
}
