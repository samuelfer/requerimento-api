package com.marhashoft.requerimentoapi.service;

import com.marhashoft.requerimentoapi.exception.DataIntegrationViolationApiException;
import com.marhashoft.requerimentoapi.model.Servidor;
import com.marhashoft.requerimentoapi.repository.ServidorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ServidorService {

    @Autowired
    ServidorRepository servidorRepository;

    public Servidor findByIdOuErro(Long id) {
        return servidorRepository.findById(id).orElseThrow(() -> new RuntimeException("Servidor não encontrado com id " + id));
    }

    public List<Servidor> listarTodos() {
        return servidorRepository.findAll(Sort.by(Sort.Direction.DESC, "nome"));
    }

    public Servidor salvar(Servidor servidor) {
        servidorJaCadastrado(servidor);
        servidor.setAtivo(true);
        return servidorRepository.save(servidor);
    }

    public Servidor atualizar(Servidor servidor) {
        servidorJaCadastrado(servidor);
        return servidorRepository.save(servidor);
    }

    private void servidorJaCadastrado(Servidor servidor) {
        Optional<Servidor> servidorExists = servidorRepository.findByNome(servidor.getNome());

        if (servidorExists.isPresent() && !servidorExists.get().getId().equals(servidor.getId())) {
            throw new DataIntegrationViolationApiException("O servidor  "
                    + servidor.getNome() + " já foi cadastrado no sistema!");
        }
    }

}
