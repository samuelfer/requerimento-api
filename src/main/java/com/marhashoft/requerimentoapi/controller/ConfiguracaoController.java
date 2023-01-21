package com.marhashoft.requerimentoapi.controller;

import com.marhashoft.requerimentoapi.model.Configuracao;
import com.marhashoft.requerimentoapi.service.ConfiguracaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/configuracoes")
public class ConfiguracaoController {

    @Autowired
    private ConfiguracaoService configuracaoService;

    @GetMapping
    public Configuracao getConfiguracao() {
        return configuracaoService.findConfiguracao();
    }

    @PostMapping
    public ResponseEntity<Configuracao> cadastrar(@Valid @RequestBody Configuracao configuracao) {
        return new ResponseEntity<>(configuracaoService.salvar(configuracao), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Configuracao> atualizar(@Valid @RequestBody Configuracao configuracao) {
        return new ResponseEntity<>(configuracaoService.salvar(configuracao), HttpStatus.CREATED);
    }
}
