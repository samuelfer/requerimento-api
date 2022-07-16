package com.marhashoft.requerimentoapi.controller;

import com.marhashoft.requerimentoapi.model.Requerimento;
import com.marhashoft.requerimentoapi.service.RequerimentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/requerimentos")
public class RequerimentoController {

    @Autowired
    private RequerimentoService requerimentoService;

    @GetMapping
    public List<Requerimento> getAll() {
        return requerimentoService.listarTodos();
    }

    @PostMapping
    public ResponseEntity<Requerimento> cadastrar(@Valid @RequestBody Requerimento requerimento) {
        return new ResponseEntity(requerimentoService.salvar(requerimento), HttpStatus.CREATED);
    }
}
