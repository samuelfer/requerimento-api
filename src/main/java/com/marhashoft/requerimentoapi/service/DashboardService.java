package com.marhashoft.requerimentoapi.service;

import com.marhashoft.requerimentoapi.TipoPessoaEnum;
import com.marhashoft.requerimentoapi.model.DashboardCount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class DashboardService {

    @Autowired
    private PessoaService pessoaService;
    @Autowired
    private RequerimentoService requerimentoService;
    @Autowired
    private UsuarioService usuarioService;

    public DashboardCount counts() {
        DashboardCount count = new DashboardCount();

        count.setQtdVereador(pessoaService.countByTipoPessoa(TipoPessoaEnum.TIPO_VEREADOR.getId()));
        count.setQtdRequerimento(requerimentoService.countRequerimento());
        count.setQtdUsuario(usuarioService.countUsuario());
        count.setQtdServidor(pessoaService.countByTipoPessoa(TipoPessoaEnum.TIPO_SERVIDOR.getId()));
        return count;
    }
}
