package com.marhashoft.requerimentoapi.repository;

import com.marhashoft.requerimentoapi.model.Usuario;
import com.marhashoft.requerimentoapi.model.dto.IUsuarioResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByUsername(String email);
    Boolean existsByUsername(String email);

    @Query(value = "select u.id, u.nome, u.username, u.ativo from usuario u order by u.nome asc", nativeQuery = true)
    List<IUsuarioResponse> findAllUsuarios();

    Optional<IUsuarioResponse> findUsuarioById(Long id);
}
