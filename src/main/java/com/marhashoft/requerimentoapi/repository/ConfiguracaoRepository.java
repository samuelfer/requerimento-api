package com.marhashoft.requerimentoapi.repository;

import com.marhashoft.requerimentoapi.model.Configuracao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfiguracaoRepository extends JpaRepository<Configuracao, Integer> {

    @Query(value = "select * from configuracao limit 1", nativeQuery = true)
    Configuracao findConfiguracao();
}
