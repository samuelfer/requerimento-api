package com.marhashoft.requerimentoapi.controller;

import com.marhashoft.requerimentoapi.model.Cargo;
import com.marhashoft.requerimentoapi.model.TipoServidor;
import com.marhashoft.requerimentoapi.service.TipoServidorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/tipo-servidores")
public class TipoServidorController {

    @Autowired
    private TipoServidorService tipoServidorService;

    @GetMapping
    public List<TipoServidor> listarTodos() {
        return tipoServidorService.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TipoServidor> listarPorId(@PathVariable("id") Long id) {
        return new ResponseEntity<>(tipoServidorService.findByIdOuErro(id), HttpStatus.OK);
    }

    //    @PreAuthorize("hasAnyRole('USUARIO')")
    @PostMapping
    public ResponseEntity<TipoServidor> cadastrar(@Valid @RequestBody TipoServidor tipoServidor) {
        return new ResponseEntity(tipoServidorService.salvar(tipoServidor), HttpStatus.CREATED);
    }


    //    @PreAuthorize("hasAnyRole('USUARIO')")
    @PutMapping
    public ResponseEntity<TipoServidor> atualizar(@Valid @RequestBody TipoServidor tipoServidor) {
        return new ResponseEntity(tipoServidorService.salvar(tipoServidor), HttpStatus.CREATED);
    }

    //    @PreAuthorize("hasAnyRole('USUARIO')")
    @PutMapping("/inativar")
    public ResponseEntity<TipoServidor> inativar(@Valid @RequestBody TipoServidor tipoServidor) {
        tipoServidorService.inativar(tipoServidor);
        return new ResponseEntity(null, HttpStatus.OK);
    }
}
