package com.marhashoft.requerimentoapi.controller;

import com.marhashoft.requerimentoapi.model.Gestao;
import com.marhashoft.requerimentoapi.model.dto.GestaoDTO;
import com.marhashoft.requerimentoapi.service.GestaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/gestoes")
public class GestaoController {

    @Autowired
    private GestaoService gestaoService;

    @GetMapping
    public List<Gestao> listarTodos() {
        return gestaoService.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Gestao> listarPorId(@PathVariable("id") Long id) {
        return new ResponseEntity<>(gestaoService.findByIdOuErro(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Gestao> cadastrar(@Valid @RequestBody GestaoDTO gestaoDTO) {
        return new ResponseEntity(gestaoService.cadastrar(gestaoDTO), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Gestao> atualizar(@Valid @RequestBody GestaoDTO gestaoDTO) {
        return new ResponseEntity(gestaoService.atualizar(gestaoDTO), HttpStatus.CREATED);
    }
}
