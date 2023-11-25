package com.marhashoft.requerimentoapi.controller;

import com.marhashoft.requerimentoapi.model.Cargo;
import com.marhashoft.requerimentoapi.model.dto.CargoDTO;
import com.marhashoft.requerimentoapi.service.CargoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/cargos")
public class CargoController {

    @Autowired
    private CargoService cargoService;

    @GetMapping
    public List<Cargo> listarTodos() {
        return cargoService.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cargo> listarPorId(@PathVariable("id") Long id) {
        return new ResponseEntity<>(cargoService.findByIdOuErro(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Cargo> cadastrar(@Valid @RequestBody CargoDTO cargoDTO) {
        return new ResponseEntity(cargoService.salvar(cargoDTO), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Cargo> atualizar(@Valid @RequestBody CargoDTO cargoDTO) {
        return new ResponseEntity(cargoService.salvar(cargoDTO), HttpStatus.CREATED);
    }
}
