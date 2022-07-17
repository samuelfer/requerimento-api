package com.marhashoft.requerimentoapi.service;

import com.marhashoft.requerimentoapi.model.Requerimento;
import com.marhashoft.requerimentoapi.repository.RequerimentoRepository;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import static org.springframework.http.MediaType.APPLICATION_PDF_VALUE;

@Service
public class RequerimentoService {

    @Autowired
    private RequerimentoRepository repository;
    @Autowired
    private ResourceLoader resourceLoader;

    public Requerimento findByIdOuErro(Long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Requerimento não encontrado com id " + id));
    }

    public List<Requerimento> listarTodos() {
        return repository.findAll();
    }

    public Requerimento salvar(Requerimento requerimento) {
        return repository.save(requerimento);
    }

    public void gerarPdf(Requerimento requerimento, HttpServletResponse response)  throws Exception {

//        try {
//            JasperReport pdfRequerimentoCompilado = JasperCompileManager.compileReport("src/main/resources/jasper/requerimento.jrxml");

        Resource resource = resourceLoader.getResource(ResourceLoader.CLASSPATH_URL_PREFIX + "jasper/requerimento.jasper");
        System.out.println("Resource "+resource);
        JasperReport report = (JasperReport) JRLoader.loadObject(resource.getInputStream());

        JRBeanCollectionDataSource dataSource =
                    new JRBeanCollectionDataSource(Collections.singletonList(""));

        System.out.println("PARAMS "+preencherParametros(requerimento));
            JasperPrint pdfRequerimentoPreenchido = JasperFillManager
                    .fillReport(report, preencherParametros(requerimento), dataSource);

            byte[] pdfByteArray =  JasperExportManager.exportReportToPdf(pdfRequerimentoPreenchido);

            File file = transformeByteParaFile(pdfByteArray, requerimento);
            downloadArquivo(file, response);

//        } catch (JRException e) {
//            Logger.getLogger(e.getMessage());
//        }
//        return new byte[0];
    }

    private Map<String, Object> preencherParametros(Requerimento requerimento) {
        Map<String, Object> parametros = new HashMap<>();
//        parametros.put("motivo", requerimento.getMotivo());
        parametros.put("justificativa", requerimento.getJustificativa());
        parametros.put("numero", requerimento.getNumero());
//
//        return parametros;
        return parametros;
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

    public void downloadArquivo(File arquivo, HttpServletResponse response) throws Exception {
        response.setContentType(APPLICATION_PDF_VALUE);
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "filename=\"" + arquivo.getName() + "\"");
        IOUtils.copy(new FileInputStream(arquivo), response.getOutputStream());
        response.flushBuffer();
    }

}
