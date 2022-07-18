package com.marhashoft.requerimentoapi.controller;

import com.marhashoft.requerimentoapi.model.Pessoa;
import com.marhashoft.requerimentoapi.model.Requerimento;
import com.marhashoft.requerimentoapi.service.PessoaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/pessoas")
public class PessoaController {

    @Autowired
    PessoaService pessoaService;

    @GetMapping
    public List<Pessoa> listarTodos() {
        return pessoaService.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> listarPorId(@PathVariable("id") Long id) {
        try {
            Pessoa pessoa = pessoaService.findByIdOuErro(id);
            return new ResponseEntity<>(pessoa, HttpStatus.OK);
        } catch (Exception e) {
            Logger.getLogger(e.getMessage());
            return new ResponseEntity<>(Arrays.asList(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping
    public ResponseEntity<Requerimento> cadastrar(@Valid @RequestBody Pessoa pessoa) {
        return new ResponseEntity(pessoaService.salvar(pessoa), HttpStatus.CREATED);
    }
}
