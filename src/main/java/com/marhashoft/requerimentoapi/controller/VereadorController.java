package com.marhashoft.requerimentoapi.controller;

import com.marhashoft.requerimentoapi.model.Vereador;
import com.marhashoft.requerimentoapi.model.dto.VereadorDTO;
import com.marhashoft.requerimentoapi.service.VereadorService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/vereadores")
public class VereadorController {

    @Autowired
    VereadorService vereadorService;
    @Autowired
    ModelMapper modelMapper;

    @GetMapping
    public List<Vereador> listarVereadores() {
        return vereadorService.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Vereador> listarPorId(@PathVariable("id") Long id) {
        return new ResponseEntity<>(vereadorService.findByIdOuErro(id), HttpStatus.OK);
    }

//    @PreAuthorize("hasAnyRole('USUARIO')")
    @PostMapping
    public ResponseEntity<Vereador> cadastrar(@Valid @RequestBody VereadorDTO vereadorDTO) {
        Vereador vereador = modelMapper.map(vereadorDTO, Vereador.class);
        return new ResponseEntity<>(vereadorService.salvar(vereador), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Vereador> atualizar(@Valid @RequestBody VereadorDTO vereadorDTO) {
        Vereador vereador = modelMapper.map(vereadorDTO, Vereador.class);
        return new ResponseEntity<>(vereadorService.atualizar(vereador), HttpStatus.CREATED);
    }
}
