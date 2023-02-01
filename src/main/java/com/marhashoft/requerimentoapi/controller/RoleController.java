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
@RequestMapping("/perfis")
public class RoleController {

    @Autowired
    private RoleService roleService;
    @Autowired
    private JasperService jasperService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping
    public List<Role> listarTodos() {
        return roleService.listarTodos();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<Role> listarPorId(@PathVariable("id") Long id) {
        Role role = roleService.findByIdOuErro(id);
        return new ResponseEntity<>(role, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public ResponseEntity<Role> cadastrar(@Valid @RequestBody Role role) {
        return new ResponseEntity<>(roleService.salvar(role), HttpStatus.CREATED);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping
    public ResponseEntity<Role> atualizar(@Valid @RequestBody Role role) {
        return new ResponseEntity<>(roleService.salvar(role), HttpStatus.CREATED);
    }
}
