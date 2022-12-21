package com.marhashoft.requerimentoapi.service;

import com.marhashoft.requerimentoapi.exception.DataIntegrationViolationApiException;
import com.marhashoft.requerimentoapi.jasper.JasperService;
import com.marhashoft.requerimentoapi.model.Requerimento;
import com.marhashoft.requerimentoapi.model.Usuario;
import com.marhashoft.requerimentoapi.repository.RequerimentoRepository;
import com.marhashoft.requerimentoapi.shared.GeradorNumeroRequerimentoService;
import com.marhashoft.requerimentoapi.usuario.UsuarioLogado;
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
    @Autowired
    private UsuarioService usuarioService;

    public Requerimento findByIdOuErro(Long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Requerimento não encontrado com id " + id));
    }

    public List<Requerimento> listarTodos() {
        return repository.findAll();
    }

    public Requerimento cadastrar(Requerimento requerimento) {
        numeroDeRequerimentoJaCadastrado(requerimento);
        requerimento.setUsuario(usuarioService.getusuarioLogadoOuErro());
        return repository.save(requerimento);
    }

    public Requerimento atualizar(Requerimento requerimento) {
        if (requerimento.getId() == null) {
            throw new RuntimeException("Requerimento não encontrado.");
        }
        findByIdOuErro(requerimento.getId());
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

    public Long countRequerimento() {
        return repository.count();
    }
}
