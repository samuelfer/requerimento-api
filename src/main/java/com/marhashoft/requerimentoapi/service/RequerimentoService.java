package com.marhashoft.requerimentoapi.service;

import com.marhashoft.requerimentoapi.model.Requerimento;
import com.marhashoft.requerimentoapi.repository.RequerimentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RequerimentoService {

    @Autowired
    private RequerimentoRepository repository;

    public List<Requerimento> listarTodos() {
        return repository.findAll();
    }

    public Requerimento salvar(Requerimento requerimento) {
        return repository.save(requerimento);
    }
}
