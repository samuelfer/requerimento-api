package com.marhashoft.requerimentoapi.controller;

import com.marhashoft.requerimentoapi.model.Requerimento;
import com.marhashoft.requerimentoapi.service.RequerimentoService;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import static org.springframework.http.MediaType.APPLICATION_PDF_VALUE;

@RestController
@RequestMapping("/requerimentos")
public class RequerimentoController {

    @Autowired
    private RequerimentoService requerimentoService;

    @GetMapping
    public List<Requerimento> listarTodos() {
        return requerimentoService.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> listarPorId(@PathVariable("id") Long id) {
        try {
            Requerimento requerimento = requerimentoService.findByIdOuErro(id);
            return new ResponseEntity<>(requerimento, HttpStatus.OK);
        } catch (Exception e) {
            Logger.getLogger(e.getMessage());
            return new ResponseEntity<>(Arrays.asList(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasAnyRole('USUARIO')")
    @PostMapping
    public ResponseEntity<?> cadastrar(@Valid @RequestBody Requerimento requerimento) {
            return new ResponseEntity<>(requerimentoService.salvar(requerimento), HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('USUARIO')")
    @GetMapping("/{id}/gerarpdf")
    public void gerarPdf(@PathVariable("id") Long id, HttpServletResponse response) {
        try {
            Requerimento requerimento = requerimentoService.findByIdOuErro(id);
            requerimentoService.gerarPdfRequerimento(requerimento, response);
        } catch (Exception e) {
            Logger.getLogger(e.getMessage());
        }
    }
}
