package com.marhashoft.requerimentoapi.controller;

import com.marhashoft.requerimentoapi.model.Servidor;
import com.marhashoft.requerimentoapi.model.dto.ServidorDTO;
import com.marhashoft.requerimentoapi.service.ServidorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/servidores")
public class ServidorController {

    @Autowired
    ServidorService servidorService;

    @GetMapping
    public List<Servidor> listarServidores() {
        return servidorService.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Servidor> listarPorId(@PathVariable("id") Long id) {
        return new ResponseEntity<>(servidorService.findByIdOuErro(id), HttpStatus.OK);
    }

    //@PreAuthorize("hasAnyRole('USUARIO')")
    @PostMapping
    public ResponseEntity<Servidor> cadastrar(@Valid @RequestBody ServidorDTO servidorDTO) {
        return new ResponseEntity<>(servidorService.salvar(servidorDTO), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Servidor> atualizar(@Valid @RequestBody ServidorDTO servidorDTO) {
        return new ResponseEntity<>(servidorService.atualizar(servidorDTO), HttpStatus.CREATED);
    }
}
