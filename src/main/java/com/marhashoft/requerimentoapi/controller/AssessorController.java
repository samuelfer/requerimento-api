package com.marhashoft.requerimentoapi.controller;

import com.marhashoft.requerimentoapi.model.Assessor;
import com.marhashoft.requerimentoapi.model.dto.AssessorDTO;
import com.marhashoft.requerimentoapi.service.AssessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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
    public ResponseEntity<Assessor> listarPorId(@PathVariable("id") Long id) {
        return new ResponseEntity<>(assessorService.findByIdOuErro(id), HttpStatus.OK);
    }

//    @PreAuthorize("hasAnyRole('USUARIO')")
    @PostMapping
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<Assessor> cadastrar(@Valid @RequestBody AssessorDTO assessorDTO) {
        return new ResponseEntity<>(assessorService.salvar(assessorDTO), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Assessor> atualizar(@Valid @RequestBody AssessorDTO assessorDTO) {
        return new ResponseEntity<>(assessorService.atualizar(assessorDTO), HttpStatus.CREATED);
    }
}
