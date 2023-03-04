package com.marhashoft.requerimentoapi.jasper;

import com.marhashoft.requerimentoapi.model.Configuracao;
import com.marhashoft.requerimentoapi.model.Oficio;
import com.marhashoft.requerimentoapi.model.Requerimento;
import com.marhashoft.requerimentoapi.service.ConfiguracaoService;
import com.marhashoft.requerimentoapi.util.DateUtil;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
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
import java.util.*;

import static org.springframework.http.MediaType.APPLICATION_PDF_VALUE;

@Service
public class JasperService {

    @Autowired
    private ResourceLoader resourceLoader;
    @Autowired
    private JasperPropriedades jasperPropriedades;
    @Autowired
    private ConfiguracaoService configuracaoService;
    @Autowired
    private JasperPropriedades properties;


    public void gerarPdf(Requerimento requerimento, HttpServletResponse response,
                         String caminhoArquivo, String nomeArquivo)  throws Exception {
        Resource resource = resourceLoader.getResource(ResourceLoader.CLASSPATH_URL_PREFIX + caminhoArquivo);

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
                               String caminhoArquivo, String nomeArquivo) {
        try {
            Resource resource = resourceLoader.getResource(ResourceLoader.CLASSPATH_URL_PREFIX + caminhoArquivo);

            JasperReport report = (JasperReport) JRLoader.loadObject(resource.getInputStream());

            JRBeanCollectionDataSource dataSource =
                    new JRBeanCollectionDataSource(Collections.singletonList(""));

            JasperPrint pdfRequerimentoPreenchido = JasperFillManager
                    .fillReport(report, preencherParametros(oficio), dataSource);

            byte[] pdfByteArray =  JasperExportManager.exportReportToPdf(pdfRequerimentoPreenchido);

            File file = transformeByteParaFile(pdfByteArray, oficio, nomeArquivo);
            downloadArquivo(file, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        parametros.put("vereador", requerimento.getVereador().getNome().toUpperCase());

        Configuracao configuracao = configuracaoService.findConfiguracao();

        String textoPadraoRequerimento = null;
        if (configuracao != null) {
            textoPadraoRequerimento = configuracao.getTextoPadraoRequerimento();
        }

        parametros.put("textoPadraoPessoa", requerimento.getVereador().getNome()
                +" , Vereador com assento nesta Casa Legislativa depois da tramitação regimental vem requerer:");
        parametros.put("textoPadrao", (!textoPadraoRequerimento.isEmpty() && textoPadraoRequerimento != null ? textoPadraoRequerimento : "")+" " +new java.text.SimpleDateFormat("dd MMMM yyyy").format(new Date())+".");
        return parametros;
    }

    private Map<String, Object> preencherParametros(Oficio oficio) {

        Map<String, Object> parametros = new HashMap<>();
        parametros.put("logo", getResourcePath(this.jasperPropriedades.getLogo()));
        parametros.put("localAndData", "Mamanguape, "+new java.text.SimpleDateFormat("dd MMMM yyyy").format(DateUtil.transformeParaDate(oficio.getDataOficio()))+".");
        parametros.put("assunto", oficio.getAssunto());
        parametros.put("numero", "OFÍCIO Nº "+oficio.getNumero());
        parametros.put("assinante", oficio.getAssinante().getNome().toUpperCase());
        parametros.put("destinatario", oficio.getDestinatario());
        parametros.put("cargoDestinatario", oficio.getCargoDestinatario());
        parametros.put("texto", oficio.getTexto());
        parametros.put("formaTratamentoDestinatario", oficio.getPronomeTratamento().getDescricao());
        parametros.put("textoPadraoAjuda", "Qualquer eventual dúvida estamos à disposição. Certo do seu pronto atendimento, elevo votos de alta estima.");
        return parametros;
    }

    public void downloadArquivo(File arquivo, HttpServletResponse response) {
        try {
            response.setContentType(APPLICATION_PDF_VALUE);
            response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "filename=\"" + arquivo.getName() + "\"");
            IOUtils.copy(new FileInputStream(arquivo), response.getOutputStream());
            response.flushBuffer();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private String getResourcePath(String property) {
        try {
            return new ClassPathResource(property).getURL().toString();
        } catch (IOException e) {
            return null;
        }
    }

    public byte[] gerarPDF(Oficio oficio) throws Exception {
        Map<String, Object> parametros =
                preencherParametros(oficio);

        return gerarPDFByteArray(parametros, properties.getOficioPreview());
    }

    public byte[] gerarPDF(Requerimento requerimento) throws Exception {
        Map<String, Object> parametros =
                preencherParametros(requerimento);
        return gerarPDFByteArray(parametros, properties.getRequerimentoPreview());
    }

    private byte[] gerarPDFByteArray(Map<String, Object> parameters, String caminhoJasper)
            throws Exception {

        JasperReport report = carregarJasper(caminhoJasper);

        JRBeanCollectionDataSource dataSource =
                new JRBeanCollectionDataSource(Collections.singletonList(""));
        JasperPrint print = JasperFillManager.fillReport(report, parameters, dataSource);
        return JasperExportManager.exportReportToPdf(print);
    }

    private JasperReport carregarJasper(String caminho) throws JRException {
        final JasperDesign jasperDesign =
                JRXmlLoader.load(JasperService.class.getResourceAsStream(caminho));

        return JasperCompileManager.compileReport(jasperDesign);
    }
}
