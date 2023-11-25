package com.marhashoft.requerimentoapi.service;

import com.marhashoft.requerimentoapi.exception.DataIntegrationViolationApiException;
import com.marhashoft.requerimentoapi.model.Gestao;
import com.marhashoft.requerimentoapi.model.dto.GestaoDTO;
import com.marhashoft.requerimentoapi.repository.GestaoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GestaoService {

    @Autowired
    private GestaoRepository gestaoRepository;
    @Autowired
    ModelMapper modelMapper;

    public Gestao findByIdOuErro(Long id) {
        return gestaoRepository.findById(id).orElseThrow(() -> new RuntimeException("Gestão não encontrada " + id));
    }

    public List<Gestao> listarTodos() {
        return gestaoRepository.findAll();
    }

    public GestaoDTO cadastrar(GestaoDTO gestaoDTO) {
        Gestao gestao = gestaoDTOToGestao(gestaoDTO);
        gestaoJaCadastrada(gestao);
        return gestaoToGestaoDTO(gestaoRepository.save(gestao));
    }

    public GestaoDTO atualizar(GestaoDTO gestaoDTO) {
        if (gestaoDTO.getId() == null) {
            throw new RuntimeException("Gestão não encontrada.");
        }
        findByIdOuErro(gestaoDTO.getId());
        Gestao gestao = gestaoDTOToGestao(gestaoDTO);
        gestaoJaCadastrada(gestao);
        return gestaoToGestaoDTO(gestaoRepository.save(gestao));
    }

    public Optional<Gestao> findById(Long id) {
        return gestaoRepository.findById(id);
    }

    public void gestaoJaCadastrada(Gestao gestao) {
        if (gestao.getDataInicio() != null && gestao.getDataFim() != null) {
            Optional<Gestao> gestaoExists = gestaoRepository
                    .findByDataInicioAndDataFim(gestao.getDataInicio(), gestao.getDataFim());

            if (gestaoExists.isPresent() && !gestaoExists.get().getId().equals(gestao.getId())) {
                throw new DataIntegrationViolationApiException("A gestão já foi cadastrado no sistema!");
            }
        }
    }

    private GestaoDTO gestaoToGestaoDTO(Gestao gestao) {
        return modelMapper.map(gestao, GestaoDTO.class);
    }

    private Gestao gestaoDTOToGestao(GestaoDTO gestaoDTO) {
        return modelMapper.map(gestaoDTO, Gestao.class);
    }
}
