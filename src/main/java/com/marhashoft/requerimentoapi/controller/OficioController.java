package com.marhashoft.requerimentoapi.controller;

import com.marhashoft.requerimentoapi.model.Oficio;
import com.marhashoft.requerimentoapi.model.Pessoa;
import com.marhashoft.requerimentoapi.service.OficioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/oficios")
public class OficioController {

    @Autowired
    private OficioService oficioService;

    @GetMapping
    public List<Oficio> listarTodos() {
        return oficioService.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Oficio> listarPorId(@PathVariable("id") Long id) {
        Oficio oficio = oficioService.findByIdOuErro(id);
        return new ResponseEntity<>(oficio, HttpStatus.OK);
    }

    @GetMapping("/assinantes")
    public List<Pessoa> listarAssinantes() {
        return oficioService.listarAssinantesOficio();
    }

    //    @PreAuthorize("hasAnyRole('USUARIO')")
    @PostMapping
    public ResponseEntity<Oficio> cadastrar(@Valid @RequestBody Oficio oficio) {
        return new ResponseEntity<>(oficioService.cadastrar(oficio), HttpStatus.CREATED);
    }

    //    @PreAuthorize("hasAnyRole('USUARIO')")
    @PutMapping
    public ResponseEntity<Oficio> atualizar(@Valid @RequestBody Oficio oficio) {
        return new ResponseEntity<>(oficioService.atualizar(oficio), HttpStatus.CREATED);
    }

    //    @PreAuthorize("hasAnyRole('USUARIO')")
    @GetMapping("/{id}/gerarpdf")
    public void gerarPdf(@PathVariable("id") Long id, HttpServletResponse response) {
        try {
            Oficio oficio = oficioService.findByIdOuErro(id);
            oficioService.gerarPdfOficio(oficio, response);
        } catch (Exception e) {
            Logger.getLogger(e.getMessage());
        }
    }
}
