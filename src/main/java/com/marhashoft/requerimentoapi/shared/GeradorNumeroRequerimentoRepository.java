package com.marhashoft.requerimentoapi.shared;

import com.marhashoft.requerimentoapi.shared.model.GeradorNumero;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GeradorNumeroRequerimentoRepository  extends JpaRepository<GeradorNumero, Long>
{
    Optional<GeradorNumero> findByAno(int ano);
}
