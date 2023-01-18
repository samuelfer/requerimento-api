package com.marhashoft.requerimentoapi.jasper;

import com.marhashoft.requerimentoapi.model.Oficio;
import com.marhashoft.requerimentoapi.model.Requerimento;
import com.marhashoft.requerimentoapi.util.DateUtil;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
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
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_PDF_VALUE;

@Service
public class JasperService {

    @Autowired
    private ResourceLoader resourceLoader;
    @Autowired
    private JasperPropriedades jasperPropriedades;


    public void gerarPdf(Requerimento requerimento, HttpServletResponse response,
                         String caminhoArquivo, String nomeArquivo)  throws Exception {
        Resource resource = resourceLoader.getResource(ResourceLoader.CLASSPATH_URL_PREFIX + "/jasper/"+caminhoArquivo);

        JasperReport report = (JasperReport) JRLoader.loadObject(resource.getInputStream());

        JRBeanCollectionDataSource dataSource =
                new JRBeanCollectionDataSource(Collections.singletonList(""));

        JasperPrint pdfRequerimentoPreenchido = JasperFillManager
                .fillReport(report, preencherParametros(requerimento), dataSource);

        byte[] pdfByteArray =  JasperExportManager.exportReportToPdf(pdfRequerimentoPreenchido);

        File file = transformeByteParaFile(pdfByteArray, requerimento, nomeArquivo);
        downloadArquivo(file, response);
    }

    public void gerarPdf(Oficio oficio, HttpServletResponse response,
                               String caminhoArquivo, String nomeArquivo)  throws Exception {
        Resource resource = resourceLoader.getResource(ResourceLoader.CLASSPATH_URL_PREFIX + "/jasper/"+caminhoArquivo);

        JasperReport report = (JasperReport) JRLoader.loadObject(resource.getInputStream());

        JRBeanCollectionDataSource dataSource =
                new JRBeanCollectionDataSource(Collections.singletonList(""));

        JasperPrint pdfRequerimentoPreenchido = JasperFillManager
                .fillReport(report, preencherParametros(oficio), dataSource);

        byte[] pdfByteArray =  JasperExportManager.exportReportToPdf(pdfRequerimentoPreenchido);

        File file = transformeByteParaFile(pdfByteArray, oficio, nomeArquivo);
        downloadArquivo(file, response);
    }

    public File transformeByteParaFile(byte[] pdfByteArray, Requerimento requerimento, String nomeArquivo) {
        File file = new File (nomeArquivo+requerimento.getId()+".pdf");
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

    public File transformeByteParaFile(byte[] pdfByteArray, Oficio oficio, String nomeArquivo) {
        File file = new File (nomeArquivo+oficio.getId()+".pdf");
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


    private Map<String, Object> preencherParametros(Requerimento requerimento) {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("logo", getResourcePath(this.jasperPropriedades.getLogo()));
        parametros.put("assunto", requerimento.getAssunto());
        parametros.put("numero", "Req. Nº. "+requerimento.getNumero());
        parametros.put("pessoa", requerimento.getPessoa().getNome());
        parametros.put("textoPadraoPessoa", "\t"+requerimento.getPessoa().getNome()
                +" , Vereador com assento nesta Casa Legislativa depois da tramitação regimental vem requerer:");
        parametros.put("textoPadrao", "\t"+"O requerente pede o apoio unânime de seus pares na aprovação do presente pedido bem como por parte do Poder Executivo Municipal" +
                "\n\nSala das Sessões da Câmara Municipal de Mamanguape, em "+new java.text.SimpleDateFormat("dd MMMM yyyy").format(new Date())+".");
        return parametros;
    }

    private Map<String, Object> preencherParametros(Oficio oficio) {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("logo", getResourcePath(this.jasperPropriedades.getLogo()));
        parametros.put("localAndData", "Mamanguape em, "+new java.text.SimpleDateFormat("dd MMMM yyyy").format(DateUtil.transformeParaDate(oficio.getDataOficio()))+".");
        parametros.put("assunto", oficio.getAssunto());
        parametros.put("texto", oficio.getTexto());
        parametros.put("numero", "Ofício "+oficio.getNumero());
        parametros.put("pessoa", oficio.getPessoa().getNome());
        parametros.put("cargoPessoa", "Informar o cargo");
        parametros.put("destinatario", oficio.getDestinatario());
        parametros.put("textoPadraoPessoa", "Venho através deste, mui respeitosamente encaminhar a esta Edilidade");
        parametros.put("textoPadrao", "\t"+"Qualquer eventual dúvida estamos à disposição. Certo do seu pronto atendimento, elevo votos de alta estima.");
        return parametros;
    }

    public void downloadArquivo(File arquivo, HttpServletResponse response) throws Exception {
        response.setContentType(APPLICATION_PDF_VALUE);
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "filename=\"" + arquivo.getName() + "\"");
        IOUtils.copy(new FileInputStream(arquivo), response.getOutputStream());
        response.flushBuffer();
    }

    private String getResourcePath(String property) {
        try {
            return new ClassPathResource(property).getURL().toString();
        } catch (IOException e) {
            return null;
        }
    }
}
