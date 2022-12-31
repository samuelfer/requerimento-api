package com.marhashoft.requerimentoapi.controller;

import com.marhashoft.requerimentoapi.model.Assessor;
import com.marhashoft.requerimentoapi.model.Pessoa;
import com.marhashoft.requerimentoapi.model.Requerimento;
import com.marhashoft.requerimentoapi.service.AssessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/assessores")
public class AssessorController {

    @Autowired
    AssessorService assessorService;

    @GetMapping
    public List<Assessor> listarPessoas() {
        return assessorService.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> listarPorId(@PathVariable("id") Long id) {
        try {
            Assessor assessor = assessorService.findByIdOuErro(id);
            return new ResponseEntity<>(assessor, HttpStatus.OK);
        } catch (Exception e) {
            Logger.getLogger(e.getMessage());
            return new ResponseEntity<>(Arrays.asList(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

//    @PreAuthorize("hasAnyRole('USUARIO')")
    @PostMapping
    public ResponseEntity<Assessor> cadastrar(@Valid @RequestBody Assessor assessor) {
        return new ResponseEntity(assessorService.salvar(assessor), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Assessor> atualizar(@Valid @RequestBody Assessor assessor) {
        return new ResponseEntity(assessorService.atualizar(assessor), HttpStatus.CREATED);
    }
}
