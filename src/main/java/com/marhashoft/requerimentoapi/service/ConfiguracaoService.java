package com.marhashoft.requerimentoapi.service;

import com.marhashoft.requerimentoapi.model.Configuracao;
import com.marhashoft.requerimentoapi.repository.ConfiguracaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConfiguracaoService {

    @Autowired
    private ConfiguracaoRepository configuracaoRepository;

    public Configuracao salvar(Configuracao configuracao) {
        return configuracaoRepository.save(configuracao);
    }

    public Configuracao findConfiguracao() {
        return configuracaoRepository.findConfiguracao();
    }
}
