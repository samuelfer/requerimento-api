package com.marhashoft.requerimentoapi.service;

import com.marhashoft.requerimentoapi.TipoPessoaEnum;
import com.marhashoft.requerimentoapi.exception.DataIntegrationViolationApiException;
import com.marhashoft.requerimentoapi.jasper.JasperPropriedades;
import com.marhashoft.requerimentoapi.jasper.JasperService;
import com.marhashoft.requerimentoapi.model.Oficio;
import com.marhashoft.requerimentoapi.model.Pessoa;
import com.marhashoft.requerimentoapi.repository.OficioRepository;
import com.marhashoft.requerimentoapi.shared.GeradorNumeroRequerimentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class OficioService {

    @Autowired
    private OficioRepository oficioRepository;
    @Autowired
    private JasperService jasperService;
    @Autowired
    private JasperPropriedades jasperPropriedades;
    @Autowired
    private GeradorNumeroRequerimentoService geradorNumero;
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private PessoaService pessoaService;

    public Oficio findByIdOuErro(Long id) {
        return oficioRepository.findById(id).orElseThrow(() -> new RuntimeException("Ofício não encontrado com id " + id));
    }

    public List<Oficio> listarTodos() {
        return oficioRepository.findAll();
    }

    public Oficio cadastrar(Oficio oficio) {
        numeroOficioJaCadastrado(oficio);
        oficio.setUsuario(usuarioService.getusuarioLogadoOuErro());
        return oficioRepository.save(oficio);
    }

    public Oficio atualizar(Oficio oficio) {
        if (oficio.getId() == null) {
            throw new RuntimeException("Ofício não encontrado.");
        }
        findByIdOuErro(oficio.getId());
        numeroOficioJaCadastrado(oficio);
        return oficioRepository.save(oficio);
    }

    public void gerarPdfOficio(Oficio oficio, HttpServletResponse response) throws Exception {
        try {
            String caminhoArquivo = jasperPropriedades.getOficio();
            String nomeArquivo = "oficio";
            jasperService.gerarPdf(oficio, response, caminhoArquivo, nomeArquivo);
        } catch (Exception e) {
            System.out.println("Exception "+e.getMessage());
        }
    }

    public void numeroOficioJaCadastrado(Oficio oficio) {
        if (oficio.getNumero() != null) {
            Optional<Oficio> requerimentoExists = oficioRepository.findFirstByNumero(oficio.getNumero());

            if (requerimentoExists.isPresent() && !requerimentoExists.get().getId().equals(oficio.getId())) {
                throw new DataIntegrationViolationApiException("O número de ofício "
                        + oficio.getNumero() + " já foi cadastrado no sistema!");
            }
        }
    }

    public List<Pessoa> listarAssinantesOficio() {
        return pessoaService.listarTodos(Arrays.asList(TipoPessoaEnum.TIPO_VEREADOR.getId(), TipoPessoaEnum.TIPO_SERVIDOR.getId()));
    }

    public Optional<Oficio> findById(Long id) {
        return oficioRepository.findById(id);
    }

    public Long countOficio() {
        return oficioRepository.count();
    }
}
