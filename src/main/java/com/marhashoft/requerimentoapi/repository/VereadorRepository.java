package com.marhashoft.requerimentoapi.repository;

import com.marhashoft.requerimentoapi.model.Vereador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VereadorRepository extends JpaRepository<Vereador, Long> {
    Optional<Vereador> findByNome(String nome);

    @Query(value = "select v.* from vereador v inner join assessor a on v.pessoa_id = a.vereador_id " +
            "inner join usuario u on a.pessoa_id = u.pessoa_id  where u.pessoa_id = :assessorId", nativeQuery = true)
    List<Vereador> findVereadorByAssessorId(@Param("assessorId") Long assessorId);
}
