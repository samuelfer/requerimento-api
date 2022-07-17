package com.marhashoft.requerimentoapi.controller;

import com.marhashoft.requerimentoapi.model.Requerimento;
import com.marhashoft.requerimentoapi.service.RequerimentoService;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import static org.springframework.http.MediaType.APPLICATION_PDF_VALUE;

@RestController
@RequestMapping("/requerimentos")
public class RequerimentoController {

    @Autowired
    private RequerimentoService requerimentoService;

    @GetMapping
    public List<Requerimento> getAll() {
        return requerimentoService.listarTodos();
    }

    @PostMapping
    public ResponseEntity<Requerimento> cadastrar(@Valid @RequestBody Requerimento requerimento) {
        return new ResponseEntity(requerimentoService.salvar(requerimento), HttpStatus.CREATED);
    }

    @GetMapping("/{id}/gerarpdf")
    public void gerarPdf(@PathVariable("id") Long id, HttpServletResponse response) {
        try {
            Requerimento requerimento = requerimentoService.findByIdOuErro(id);
            requerimentoService.gerarPdf(requerimento, response);
        } catch (Exception e) {
            System.out.println("Erro ao tentar gerar o pdf "+e.getMessage());
            Logger.getLogger(e.getMessage());
        }
    }

    public void downloadArquivo(File arquivo, HttpServletResponse response) throws Exception {
        response.setContentType(APPLICATION_PDF_VALUE);
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "filename=\"" + arquivo.getName() + "\"");
        IOUtils.copy(new FileInputStream(arquivo), response.getOutputStream());
        response.flushBuffer();
    }

    public File transformeByteParaFile(byte[] pdfByteArray, Requerimento requerimento) {
        File file = new File ("requerimento"+requerimento.getId()+".pdf");
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream (file);
            fileOutputStream.write (pdfByteArray);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException ex) {

                }
            }
        }
        return file;
    }
}
