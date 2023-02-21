package com.marhashoft.requerimentoapi.service;

import com.marhashoft.requerimentoapi.exception.DataIntegrationViolationApiException;
import com.marhashoft.requerimentoapi.model.Role;
import com.marhashoft.requerimentoapi.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    public Role findByIdOuErro(Long id) {
        return roleRepository.findById(id).orElseThrow(() -> new RuntimeException("Perfil não encontrado com id " + id));
    }

    public List<Role> listarTodos() {
        return roleRepository.findAll();
    }

    public Role salvar(Role role) {
        perfilJaCadastrada(role);
        return roleRepository.save(role);
    }

    private void perfilJaCadastrada(Role role) {
        Optional<Role> perfilExists = roleRepository.findByNome(role.getNome());

        if (perfilExists.isPresent() && !perfilExists.get().getId().equals(role.getId())) {
            throw new DataIntegrationViolationApiException("O perfil  "
                    + role.getNome() + " já foi cadastrado no sistema!");
        }
    }

    public void deletar(Long id) {
        Role role = findByIdOuErro(id);
        Optional<Role> roleWithUserByRoleId = roleRepository.findRoleWithUserByRoleId(id);
        if (roleWithUserByRoleId.isPresent()) {
            throw new RuntimeException("Esse perfil não pode ser excluído pois ele está associado a usuário");
        }
        roleRepository.delete(role);
    }
}
