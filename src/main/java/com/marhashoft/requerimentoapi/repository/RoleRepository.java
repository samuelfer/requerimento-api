package com.marhashoft.requerimentoapi.repository;

import com.marhashoft.requerimentoapi.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByNome(String nome);

    @Query(value = "select r.* from usuario_roles where r.role_id = :roleId", nativeQuery = true)
    Optional<Role> findRoleWithUserByRoleId(Long roleId);
}
