package com.marhashoft.requerimentoapi.service;

import com.marhashoft.requerimentoapi.model.Requerimento;
import com.marhashoft.requerimentoapi.model.TipoPessoa;
import com.marhashoft.requerimentoapi.repository.TipoPessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TipoPessoaService {

    public static final Long TIPO_USUARIO = 1L;
    public static final Long TIPO_VEREADOR = 2L;
    public static final Long TIPO_SERVIDOR = 3L;
    public static final Long TIPO_ASSISTENTE = 4L;

    @Autowired
    private TipoPessoaRepository tipoPessoaRepository;

    public TipoPessoa findByIdOuErro(Long id) {
        return tipoPessoaRepository.findById(id).orElseThrow(() -> new RuntimeException("TipoPessoa não encontrado com id " + id));
    }

    public List<TipoPessoa> listarTodos() {
        return tipoPessoaRepository.findByAtivoTrueOrderByDescricao();
    }

    public TipoPessoa salvar(TipoPessoa tipoPessoa) {
        return tipoPessoaRepository.save(tipoPessoa);
    }

    public void inativar(TipoPessoa tipoPessoa) {
        findByIdOuErro(tipoPessoa.getId());
        tipoPessoa.setAtivo(false);
        tipoPessoaRepository.save(tipoPessoa);
    }
}
