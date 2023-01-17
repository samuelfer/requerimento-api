package com.marhashoft.requerimentoapi.controller;

import com.marhashoft.requerimentoapi.model.Vereador;
import com.marhashoft.requerimentoapi.model.dto.VereadorDTO;
import com.marhashoft.requerimentoapi.service.VereadorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/vereadores")
public class VereadorController {

    @Autowired
    VereadorService vereadorService;

    @GetMapping
    public List<Vereador> listarVereadores() {
        return vereadorService.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Vereador> listarPorId(@PathVariable("id") Long id) {
        return new ResponseEntity<>(vereadorService.findByIdOuErro(id), HttpStatus.OK);
    }

//    @PreAuthorize("hasAnyRole('USUARIO')")
    @PostMapping
    public ResponseEntity<Vereador> cadastrar(@Valid @RequestBody VereadorDTO vereadorDTO) {
        return new ResponseEntity<>(vereadorService.salvar(vereadorDTO), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Vereador> atualizar(@Valid @RequestBody VereadorDTO vereadorDTO) {
        return new ResponseEntity<>(vereadorService.atualizar(vereadorDTO), HttpStatus.CREATED);
    }
}
