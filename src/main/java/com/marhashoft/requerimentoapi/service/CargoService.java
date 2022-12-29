package com.marhashoft.requerimentoapi.service;

import com.marhashoft.requerimentoapi.model.Assessor;
import com.marhashoft.requerimentoapi.model.Cargo;
import com.marhashoft.requerimentoapi.repository.CargoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CargoService {

    @Autowired
    private CargoRepository cargoRepository;

    public Cargo findByIdOuErro(Long id) {
        return cargoRepository.findById(id).orElseThrow(() -> new RuntimeException("Cargo não encontrado com id " + id));
    }

    public List<Cargo> listarTodos() {
        return cargoRepository.findAll();
    }

    public Cargo salvar(Cargo cargo) {
        cargo.setAtivo(true);
        return cargoRepository.save(cargo);
    }
}
