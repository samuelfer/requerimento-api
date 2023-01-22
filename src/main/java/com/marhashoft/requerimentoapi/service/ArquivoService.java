package com.marhashoft.requerimentoapi.service;

import com.marhashoft.requerimentoapi.model.Arquivo;
import com.marhashoft.requerimentoapi.repository.ArquivoRepository;
import com.marhashoft.requerimentoapi.util.ArquivoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ArquivoService {

    @Autowired
    private ArquivoRepository arquivoRepository;

    public Arquivo salvar(String nomeArquivo ,MultipartFile multipartFile) {
        Arquivo arquivoNovo = new Arquivo();

        arquivoNovo.setNome(nomeArquivo);
        arquivoNovo.setSize(multipartFile.getSize());
        arquivoNovo.setUrl(nomeArquivo);
        arquivoNovo.setAtivo(true);
        arquivoNovo.setTipo(ArquivoUtil.getExtensionArquivo(nomeArquivo));

        return arquivoRepository.save(arquivoNovo);
    }
}
