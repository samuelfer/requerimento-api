package com.marhashoft.requerimentoapi.service;

import com.marhashoft.requerimentoapi.enums.TipoArquivoEnum;
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

    public Arquivo salvar(String nomeArquivo, MultipartFile multipartFile, String tipoArquivo) {
        Arquivo arquivoNovo = new Arquivo();

        arquivoNovo.setNome(nomeArquivo);
        arquivoNovo.setSize(multipartFile.getSize());
        arquivoNovo.setUrl("uploads/"+nomeArquivo);
        arquivoNovo.setAtivo(true);
        arquivoNovo.setExtension(ArquivoUtil.getExtensionArquivo(nomeArquivo));
        arquivoNovo.setTipoArquivo(TipoArquivoEnum.recuperarTipoPorNome(tipoArquivo).getNome());

        return arquivoRepository.save(arquivoNovo);
    }
}
