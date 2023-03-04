package com.marhashoft.requerimentoapi.service;

import com.marhashoft.requerimentoapi.exception.DataIntegrationViolationApiException;
import com.marhashoft.requerimentoapi.jasper.JasperPropriedades;
import com.marhashoft.requerimentoapi.jasper.JasperService;
import com.marhashoft.requerimentoapi.model.Requerimento;
import com.marhashoft.requerimentoapi.model.Usuario;
import com.marhashoft.requerimentoapi.repository.RequerimentoRepository;
import com.marhashoft.requerimentoapi.shared.GeradorNumeroRequerimentoService;
import org.springframework.beans.factory.annotation.Autowired;
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
    private JasperPropriedades jasperPropriedades;
    @Autowired
    private GeradorNumeroRequerimentoService geradorNumero;
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private AssessorService assessorService;
    @Autowired
    private VereadorService vereadorService;

    public Requerimento findByIdOuErro(Long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Requerimento não encontrado com id " + id));
    }

    public List<Requerimento> listarTodos() {
        Usuario usuarioLogado = usuarioService.getusuarioLogadoOuErro();
        if (usuarioService.usuarioLogadoIsAssessor()) {
            return repository.findByVereadorId(assessorService.getVereadorDoAssessor(usuarioLogado).getId());
        } else if(usuarioService.usuarioLogadoIsVereador()) {
            return repository.findByVereadorId(usuarioLogado.getPessoaId());
        }
        return repository.findAll();
    }

    public Requerimento cadastrar(Requerimento requerimento) {
        Usuario usuarioLogado = usuarioService.getusuarioLogadoOuErro();

        validaUsuarioLogadoPodeOperarNoRequerimento(usuarioLogado, requerimento.getVereador().getId());
        numeroDeRequerimentoJaCadastrado(requerimento);
        requerimento.setUsuario(usuarioService.getusuarioLogadoOuErro());
        return repository.save(requerimento);
    }

    public Requerimento atualizar(Requerimento requerimento) {
        if (requerimento.getId() == null) {
            throw new RuntimeException("Requerimento não encontrado.");
        }
        Usuario usuarioLogado = usuarioService.getusuarioLogadoOuErro();
        validaUsuarioLogadoPodeOperarNoRequerimento(usuarioLogado, requerimento.getVereador().getId());
        findByIdOuErro(requerimento.getId());
        numeroDeRequerimentoJaCadastrado(requerimento);
        return repository.save(requerimento);
    }

    public void gerarPdfRequerimento(Requerimento requerimento, HttpServletResponse response) throws Exception {
        String caminhoArquivo = jasperPropriedades.getRequerimento();
        String nomeArquivo = "requerimento";
        jasperService.gerarPdf(requerimento, response, caminhoArquivo, nomeArquivo);
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
        Usuario usuarioLogado = usuarioService.getusuarioLogadoOuErro();
        if (usuarioService.usuarioLogadoIsAssessor()) {
            return repository.countByVereadorId(assessorService.getVereadorDoAssessor(usuarioLogado).getId());
        } else if(usuarioService.usuarioLogadoIsVereador()) {
            return repository.countByVereadorId(usuarioLogado.getPessoaId());
        }
        return repository.count();
    }


    public void validaUsuarioLogadoPodeOperarNoRequerimento(Usuario usuarioLogado, Long vereadorId) {

        if (usuarioService.usuarioLogadoIsAssessor()) {
            assessorService.validaAssessorPodePreencherRequerimentoDoVereador(usuarioLogado, vereadorId);
        } else if(usuarioService.usuarioLogadoIsVereador()) {
            vereadorService.validaVereadorPodePreencherRequerimento(usuarioLogado, vereadorId);
        }
    }
}
