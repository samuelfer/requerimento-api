package com.marhashoft.requerimentoapi.controller;

import com.marhashoft.requerimentoapi.model.Usuario;
import com.marhashoft.requerimentoapi.model.dto.IUsuarioResponse;
import com.marhashoft.requerimentoapi.service.UsuarioService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;


@Api(value = "Usuarios", tags = "Usuarios")
@RestController
@RequestMapping(value = "/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @ApiOperation(value = "Retorna usuario por id")
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping(value = "/{id}")
    public ResponseEntity<IUsuarioResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok().body(usuarioService.findById(id));
    }

    @ApiOperation(value = "Retorna todos usuarios")
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping
    public ResponseEntity<List<IUsuarioResponse>> findAll() {
        return ResponseEntity.ok().body(usuarioService.findAll());
    }

    @ApiOperation(value = "Cadastra usuario")
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public ResponseEntity<Usuario> create(@Valid @RequestBody Usuario usuario) {
        Usuario newUsuario = usuarioService.create(usuario);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newUsuario.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @ApiOperation(value = "Atualiza usuario")
    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping
    public ResponseEntity<Usuario> update(@Valid @RequestBody Usuario usuario) {
        return ResponseEntity.ok().body(usuarioService.update(usuario));
    }

    @ApiOperation(value = "Inativa usuario")
    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/inativar")
    public ResponseEntity<Usuario> inativar(@PathVariable Long id, @Valid @RequestBody Usuario usuario) {
        usuarioService.inativar(id, usuario);
        return ResponseEntity.ok().body(null);
    }


}