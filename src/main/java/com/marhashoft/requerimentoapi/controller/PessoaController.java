package com.marhashoft.requerimentoapi.controller;

import com.marhashoft.requerimentoapi.model.Pessoa;
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

    @GetMapping("/tipo/{tipoPessoaId}")
    public List<Pessoa> listarPessoas(@PathVariable("tipoPessoaId") Long tipoPessoaId) {
        List<Pessoa> pessoas = pessoaService.listarTodos(tipoPessoaId);
        return pessoas;
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

//    @PreAuthorize("hasAnyRole('USUARIO')")
    @PostMapping
    public ResponseEntity<Pessoa> cadastrar(@Valid @RequestBody Pessoa pessoa) {
        return new ResponseEntity(pessoaService.salvar(pessoa), HttpStatus.CREATED);
    }
}
