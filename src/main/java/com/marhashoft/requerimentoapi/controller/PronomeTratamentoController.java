package com.marhashoft.requerimentoapi.controller;

import com.marhashoft.requerimentoapi.model.Cargo;
import com.marhashoft.requerimentoapi.model.PronomeTratamento;
import com.marhashoft.requerimentoapi.service.PronomeTratamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/pronomes-tratamento")
public class PronomeTratamentoController {

    @Autowired
    private PronomeTratamentoService pronomeTratamentoService;

    @GetMapping
    public List<PronomeTratamento> listarTodos() {
        return pronomeTratamentoService.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PronomeTratamento> listarPorId(@PathVariable("id") Integer id) {
        return new ResponseEntity<>(pronomeTratamentoService.findByIdOuErro(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<PronomeTratamento> cadastrar(@Valid @RequestBody PronomeTratamento pronomeTratamento) {
        return new ResponseEntity(pronomeTratamentoService.salvar(pronomeTratamento), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<PronomeTratamento> atualizar(@Valid @RequestBody PronomeTratamento pronomeTratamento) {
        return new ResponseEntity(pronomeTratamentoService.salvar(pronomeTratamento), HttpStatus.CREATED);
    }
}
