package com.marhashoft.requerimentoapi.controller;

import com.marhashoft.requerimentoapi.jasper.JasperService;
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
import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.logging.Logger;

import static org.springframework.http.MediaType.APPLICATION_PDF_VALUE;

@RestController
@RequestMapping("/requerimentos")
public class RequerimentoController {

    @Autowired
    private RequerimentoService requerimentoService;
    @Autowired
    private JasperService jasperService;

    @GetMapping
    public List<Requerimento> listarTodos() {
        return requerimentoService.listarTodos();
    }

//    @PreAuthorize("hasAnyRole('USUARIO')")
    @GetMapping("/{id}")
    public ResponseEntity<Requerimento> listarPorId(@PathVariable("id") Long id) {
        Requerimento requerimento = requerimentoService.findByIdOuErro(id);
        return new ResponseEntity<>(requerimento, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public ResponseEntity<Requerimento> cadastrar(@Valid @RequestBody Requerimento requerimento) {
        return new ResponseEntity<>(requerimentoService.cadastrar(requerimento), HttpStatus.CREATED);
    }

//    @PreAuthorize("hasAnyRole('USUARIO')")
    @PutMapping
    public ResponseEntity<Requerimento> atualizar(@Valid @RequestBody Requerimento requerimento) {
        return new ResponseEntity<>(requerimentoService.atualizar(requerimento), HttpStatus.CREATED);
    }

//    @PreAuthorize("hasAnyRole('USUARIO')")
    @GetMapping("/{id}/gerarpdf")
    public void gerarPdf(@PathVariable("id") Long id, HttpServletResponse response) {
        try {
            Requerimento requerimento = requerimentoService.findByIdOuErro(id);
            requerimentoService.gerarPdfRequerimento(requerimento, response);
        } catch (Exception e) {
            Logger.getLogger(e.getMessage());
        }
    }

    @PostMapping(value = "/preview-pdf", produces = "application/pdf")
    public void previewPDF(@RequestBody Requerimento requerimento, HttpServletResponse response) throws Exception {
        try {
            byte[] arquivo = this.jasperService.gerarPDF(requerimento);

            response.setContentType(APPLICATION_PDF_VALUE);
            response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "filename=Teste-requerimento");
            IOUtils.copy(new ByteArrayInputStream(arquivo), response.getOutputStream());
            response.flushBuffer();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
