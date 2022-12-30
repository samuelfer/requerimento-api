package com.marhashoft.requerimentoapi.service;

import com.marhashoft.requerimentoapi.exception.DataIntegrationViolationApiException;
import com.marhashoft.requerimentoapi.model.Vereador;
import com.marhashoft.requerimentoapi.repository.VereadorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
        vereadorJaCadastrado(vereador);
        vereador.setAtivo(true);
        return vereadorRepository.save(vereador);
    }

    public Vereador atualizar(Vereador vereador) {
        vereadorJaCadastrado(vereador);
        return vereadorRepository.save(vereador);
    }

    private void vereadorJaCadastrado(Vereador vereador) {
        Optional<Vereador> servidorExists = vereadorRepository.findByNome(vereador.getNome());

        if (servidorExists.isPresent() && !servidorExists.get().getId().equals(vereador.getId())) {
            throw new DataIntegrationViolationApiException("O vereador  "
                    + vereador.getNome() + " já foi cadastrado no sistema!");
        }
    }
}
