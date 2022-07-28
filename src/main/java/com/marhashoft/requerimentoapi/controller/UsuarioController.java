package com.marhashoft.requerimentoapi.controller;

import com.marhashoft.requerimentoapi.model.Usuario;
import com.marhashoft.requerimentoapi.model.dto.UsuarioDTO;
import com.marhashoft.requerimentoapi.service.UsuarioService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    @GetMapping(value = "/{id}")
    public ResponseEntity<Usuario> findById(@PathVariable Long id) {
        return ResponseEntity.ok().body(usuarioService.findByIdOuErro(id));
    }

    @ApiOperation(value = "Retorna todos usuarios")
    @GetMapping
    public ResponseEntity<List<Usuario>> findAll() {
        return ResponseEntity.ok().body(usuarioService.findAll());
    }

    @ApiOperation(value = "Cadastra usuario")
    @PostMapping
    public ResponseEntity<Usuario> create(@Valid @RequestBody UsuarioDTO usuarioDTO) {
        Usuario newUsuario = usuarioService.create(usuarioDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newUsuario.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @ApiOperation(value = "Atualiza usuario")
    @PutMapping(value = "/{id}")
    public ResponseEntity<UsuarioDTO> update(@PathVariable Long id, @Valid @RequestBody UsuarioDTO usuarioDTO) {
        UsuarioDTO usuarioResponse = usuarioService.update(id, usuarioDTO);
        return ResponseEntity.ok().body(usuarioResponse);
    }
}