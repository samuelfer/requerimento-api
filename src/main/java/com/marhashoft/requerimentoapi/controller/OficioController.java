package com.marhashoft.requerimentoapi.controller;

import com.marhashoft.requerimentoapi.jasper.JasperService;
import com.marhashoft.requerimentoapi.model.Configuracao;
import com.marhashoft.requerimentoapi.model.Oficio;
import com.marhashoft.requerimentoapi.model.Pessoa;
import com.marhashoft.requerimentoapi.service.ConfiguracaoService;
import com.marhashoft.requerimentoapi.service.OficioService;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.ByteArrayInputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.logging.Logger;

import static org.springframework.http.MediaType.APPLICATION_PDF_VALUE;

@RestController
@RequestMapping("/oficios")
public class OficioController {

    @Autowired
    private OficioService oficioService;
    @Autowired
    private JasperService jasperService;
    @Autowired
    private ConfiguracaoService configuracaoService;

    @GetMapping
    public List<Oficio> listarTodos() {
        return oficioService.listarTodos();
    }


    @GetMapping("/{id}")
    public ResponseEntity<Oficio> listarPorId(@PathVariable("id") Long id) {
        Oficio oficio = oficioService.findByIdOuErro(id);
        return new ResponseEntity<>(oficio, HttpStatus.OK);
    }

    @GetMapping("/texto-padrao")
    public ResponseEntity<Configuracao> getTextoPadraoOficio() {
        return new ResponseEntity(configuracaoService.findConfiguracao(), HttpStatus.OK);
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

    @GetMapping("/download/{id}")
    @Transactional(rollbackFor = Exception.class)
    public void download(@PathVariable Long id, HttpServletResponse response) {
        try {
            Oficio arquivo = this.oficioService.findById(id).orElseThrow(() -> new Exception("Ofício não encontrado."));
            //VERIFICAR SE O USUARIO PODE ACESSAR O ARQUIVO
            response.setContentType("application/pdf");
            response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "filename=teste-oficio");
            download(arquivo, response.getOutputStream());
            response.flushBuffer();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void download(Oficio arquivo, OutputStream output) throws Exception {
        this.jasperService.gerarPDF(arquivo);
    }

    @PostMapping(value = "/preview-pdf", produces = "application/pdf")
    public void previewPDF(@RequestBody Oficio oficio, HttpServletResponse response) throws Exception {
        try {
            byte[] arquivo = this.jasperService.gerarPDF(oficio);

            response.setContentType(APPLICATION_PDF_VALUE);
            response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "filename=Teste-oficio");
            IOUtils.copy(new ByteArrayInputStream(arquivo), response.getOutputStream());
            response.flushBuffer();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
