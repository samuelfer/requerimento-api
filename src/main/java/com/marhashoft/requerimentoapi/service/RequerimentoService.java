package com.marhashoft.requerimentoapi.service;

import com.marhashoft.requerimentoapi.exception.DataIntegrationViolationApiException;
import com.marhashoft.requerimentoapi.jasper.JasperService;
import com.marhashoft.requerimentoapi.model.Requerimento;
import com.marhashoft.requerimentoapi.repository.RequerimentoRepository;
import com.marhashoft.requerimentoapi.shared.GeradorNumeroRequerimentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;

@Service
public class RequerimentoService {

    @Autowired
    private RequerimentoRepository repository;
    @Autowired
    private JasperService jasperService;
    @Autowired
    private GeradorNumeroRequerimentoService geradorNumero;

    public Requerimento findByIdOuErro(Long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Requerimento não encontrado com id " + id));
    }

    public List<Requerimento> listarTodos() {
        return repository.findAll();
    }

    public Requerimento salvar(Requerimento requerimento) {
        numeroDeRequerimentoJaCadastrado(requerimento);
        return repository.save(requerimento);
    }

    public void gerarPdfRequerimento(Requerimento requerimento, HttpServletResponse response) throws Exception {
        try {
            String caminhoArquivo = "requerimento.jasper";
            String nomeArquivo = "requerimento";
            jasperService.gerarPdf(requerimento, response, caminhoArquivo, nomeArquivo);
        } catch (Exception e) {
            System.out.println("Exception "+e.getMessage());
        }

    }

    public void numeroDeRequerimentoJaCadastrado(Requerimento requerimento) {
        if (requerimento.getNumero() != null) {
            Optional<Requerimento> requerimentoExists = repository.findFirstByNumero(requerimento.getNumero());

            if (requerimentoExists.isPresent() && !requerimentoExists.get().getId().equals(requerimento.getId())) {
                throw new DataIntegrationViolationApiException("O número de requerimento "
                        + requerimento.getNumero() + " já foi cadastrado no sistema!");
            }
        }
    }
}
