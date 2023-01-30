package com.marhashoft.requerimentoapi.service;

import com.marhashoft.requerimentoapi.exception.DataIntegrationViolationApiException;
import com.marhashoft.requerimentoapi.model.PronomeTratamento;
import com.marhashoft.requerimentoapi.repository.PronomeTratamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PronomeTratamentoService {

    @Autowired
    PronomeTratamentoRepository pronomeTratamentoRepository;

    public PronomeTratamento findByIdOuErro(Integer id) {
        return pronomeTratamentoRepository.findById(id).orElseThrow(() -> new RuntimeException("Pronome tratamento não encontrado com id " + id));
    }

    public List<PronomeTratamento> listarTodos() {
        return pronomeTratamentoRepository.findAll(Sort.by(Sort.Direction.DESC, "descricao"));
    }

    public PronomeTratamento salvar(PronomeTratamento pronomeTratamento) {
        pronomeTratamentoJaCadastrado(pronomeTratamento);
        pronomeTratamento.setAtivo(true);
        return pronomeTratamentoRepository.save(pronomeTratamento);
    }

    public PronomeTratamento PronomeTratamento(PronomeTratamento pronomeTratamento) {
        pronomeTratamentoJaCadastrado(pronomeTratamento);
        return pronomeTratamentoRepository.save(pronomeTratamento);
    }

    private void pronomeTratamentoJaCadastrado(PronomeTratamento pronomeTratamento) {
        Optional<PronomeTratamento> assessorExists = pronomeTratamentoRepository.findByDescricao(pronomeTratamento.getDescricao());

        if (assessorExists.isPresent() && !assessorExists.get().getId().equals(pronomeTratamento.getId())) {
            throw new DataIntegrationViolationApiException("O pronome tratamento  "
                    + pronomeTratamento.getDescricao() + " já foi cadastrado no sistema!");
        }
    }
}
