package com.marhashoft.requerimentoapi.controller;

import com.marhashoft.requerimentoapi.jasper.JasperService;
import com.marhashoft.requerimentoapi.model.Role;
import com.marhashoft.requerimentoapi.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/admin/perfis")
public class RoleController {

    @Autowired
    private RoleService roleService;
    @Autowired
    private JasperService jasperService;

    @GetMapping
    public List<Role> listarTodos() {
        return roleService.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Role> listarPorId(@PathVariable("id") Long id) {
        Role role = roleService.findByIdOuErro(id);
        return new ResponseEntity<>(role, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Role> cadastrar(@Valid @RequestBody Role role) {
        return new ResponseEntity<>(roleService.salvar(role), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Role> atualizar(@Valid @RequestBody Role role) {
        return new ResponseEntity<>(roleService.salvar(role), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable("id") Long id) {
        roleService.deletar(id);
        return new ResponseEntity(null, HttpStatus.OK);
    }
}
