package com.marhashoft.requerimentoapi.service;

import com.marhashoft.requerimentoapi.model.Vereador;
import com.marhashoft.requerimentoapi.repository.VereadorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VereadorService {

    @Autowired
    VereadorRepository vereadorRepository;

    public Vereador findByIdOuErro(Long id) {
        return vereadorRepository.findById(id).orElseThrow(() -> new RuntimeException("Vereador não encontrado com id " + id));
    }

    public List<Vereador> listarTodos() {
        return vereadorRepository.findAll(Sort.by(Sort.Direction.DESC, "nome"));
    }

    public Vereador salvar(Vereador vereador) {
        vereador.setAtivo(true);
        return vereadorRepository.save(vereador);
    }
}
