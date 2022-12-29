package com.marhashoft.requerimentoapi.controller;

import com.marhashoft.requerimentoapi.model.Assessor;
import com.marhashoft.requerimentoapi.model.Cargo;
import com.marhashoft.requerimentoapi.model.Servidor;
import com.marhashoft.requerimentoapi.service.ServidorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

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
    public ResponseEntity<?> listarPorId(@PathVariable("id") Long id) {
        try {
            Servidor servidor = servidorService.findByIdOuErro(id);
            return new ResponseEntity<>(servidor, HttpStatus.OK);
        } catch (Exception e) {
            Logger.getLogger(e.getMessage());
            return new ResponseEntity<>(Arrays.asList(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    //@PreAuthorize("hasAnyRole('USUARIO')")
    @PostMapping
    public ResponseEntity<Servidor> cadastrar(@Valid @RequestBody Servidor servidor) {
        return new ResponseEntity(servidorService.salvar(servidor), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Cargo> atualizar(@Valid @RequestBody Servidor servidor) {
        return new ResponseEntity(servidorService.atualizar(servidor), HttpStatus.CREATED);
    }
}
