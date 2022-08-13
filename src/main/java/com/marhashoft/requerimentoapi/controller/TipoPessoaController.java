package com.marhashoft.requerimentoapi.controller;

import com.marhashoft.requerimentoapi.model.TipoPessoa;
import com.marhashoft.requerimentoapi.service.TipoPessoaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/tipo-pessoa")
public class TipoPessoaController {

    @Autowired
    private TipoPessoaService tipoPessoaService;

    @GetMapping
    public List<TipoPessoa> listarTodos() {
        System.out.println("Entrei no tipo pessoa");return tipoPessoaService.listarTodos();
    }

    @PreAuthorize("hasAnyRole('USUARIO')")
    @PostMapping
    public ResponseEntity<TipoPessoa> cadastrar(@Valid @RequestBody TipoPessoa tipoPessoa) {
        return new ResponseEntity(tipoPessoaService.salvar(tipoPessoa), HttpStatus.CREATED);
    }


    @PreAuthorize("hasAnyRole('USUARIO')")
    @PutMapping
    public ResponseEntity<TipoPessoa> atualizar(@Valid @RequestBody TipoPessoa tipoPessoa) {
        return new ResponseEntity(tipoPessoaService.salvar(tipoPessoa), HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('USUARIO')")
    @PutMapping("/inativar")
    public ResponseEntity<TipoPessoa> inativar(@Valid @RequestBody TipoPessoa tipoPessoa) {
        tipoPessoaService.inativar(tipoPessoa);
        return new ResponseEntity(null, HttpStatus.OK);
    }
}
