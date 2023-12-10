package com.marhashoft.requerimentoapi.repository;

import com.marhashoft.requerimentoapi.model.TipoPessoa;
import com.marhashoft.requerimentoapi.model.TipoServidor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TipoServidorRepository extends JpaRepository<TipoServidor, Long> {

    List<TipoServidor> findByAtivoTrueOrderByDescricao();

    Long countById(Long tipoServidorId);
}
