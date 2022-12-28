package com.marhashoft.requerimentoapi.service;

import com.marhashoft.requerimentoapi.TipoPessoaEnum;
import com.marhashoft.requerimentoapi.model.Pessoa;
import com.marhashoft.requerimentoapi.repository.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PessoaService {

    @Autowired
    PessoaRepository pessoaRepository;

    public Pessoa findByIdOuErro(Long id) {
        return pessoaRepository.findById(id).orElseThrow(() -> new RuntimeException("Vereador não encontrada com id " + id));
    }

    public List<Pessoa> listarTodos(Long tipoPessoaId) {
        return pessoaRepository.findByAtivoTrueAndTipoPessoaIdOrderByNome(tipoPessoaId);
    }

    public Pessoa salvar(Pessoa pessoa) {
        pessoa.setAtivo(true);
        return pessoaRepository.save(pessoa);
    }

    public Long countByTipoPessoa(Long tipoPessoaId) {
        return pessoaRepository.countByTipoPessoaId(tipoPessoaId);
    }
}
