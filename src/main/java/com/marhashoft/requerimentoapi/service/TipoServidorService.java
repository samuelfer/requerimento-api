package com.marhashoft.requerimentoapi.service;

import com.marhashoft.requerimentoapi.model.Pessoa;
import com.marhashoft.requerimentoapi.model.TipoPessoa;
import com.marhashoft.requerimentoapi.model.TipoServidor;
import com.marhashoft.requerimentoapi.repository.PessoaRepository;
import com.marhashoft.requerimentoapi.repository.TipoServidorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TipoServidorService {

    @Autowired
    TipoServidorRepository tipoServidorRepository;

    public TipoServidor findByIdOuErro(Long id) {
        return tipoServidorRepository.findById(id).orElseThrow(() -> new RuntimeException("Tipo servidor não encontrado com id " + id));
    }

    public List<TipoServidor> listarTodos() {
        return tipoServidorRepository.findAll();
    }

    public TipoServidor salvar(TipoServidor tipoServidor) {
        tipoServidor.setAtivo(true);
        return tipoServidorRepository.save(tipoServidor);
    }

    public Long countByTipoServidor(Long tipoServidorId) {
        return tipoServidorRepository.countById(tipoServidorId);
    }

    public void inativar(TipoServidor tipoServidor) {
        findByIdOuErro(tipoServidor.getId());
        tipoServidor.setAtivo(false);
        tipoServidorRepository.save(tipoServidor);
    }
}
