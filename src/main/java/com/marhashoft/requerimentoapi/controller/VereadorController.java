package com.marhashoft.requerimentoapi.controller;

import com.marhashoft.requerimentoapi.model.Vereador;
import com.marhashoft.requerimentoapi.service.VereadorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

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
    public ResponseEntity<?> listarPorId(@PathVariable("id") Long id) {
        try {
            Vereador vereador = vereadorService.findByIdOuErro(id);
            return new ResponseEntity<>(vereador, HttpStatus.OK);
        } catch (Exception e) {
            Logger.getLogger(e.getMessage());
            return new ResponseEntity<>(Arrays.asList(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

//    @PreAuthorize("hasAnyRole('USUARIO')")
    @PostMapping
    public ResponseEntity<Vereador> cadastrar(@Valid @RequestBody Vereador vereador) {
        return new ResponseEntity(vereadorService.salvar(vereador), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Vereador> atualizar(@Valid @RequestBody Vereador vereador) {
        return new ResponseEntity(vereadorService.atualizar(vereador), HttpStatus.CREATED);
    }
}
