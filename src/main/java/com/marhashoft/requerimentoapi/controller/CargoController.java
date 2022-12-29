package com.marhashoft.requerimentoapi.controller;

import com.marhashoft.requerimentoapi.model.Cargo;
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
    public ResponseEntity<?> listarPorId(@PathVariable("id") Long id) {
        try {
            Cargo cargo = cargoService.findByIdOuErro(id);
            return new ResponseEntity<>(cargo, HttpStatus.OK);
        } catch (Exception e) {
            Logger.getLogger(e.getMessage());
            return new ResponseEntity<>(Arrays.asList(e.getMessage()), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping
    public ResponseEntity<Cargo> cadastrar(@Valid @RequestBody Cargo cargo) {
        return new ResponseEntity(cargoService.salvar(cargo), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Cargo> atualizar(@Valid @RequestBody Cargo cargo) {
        return new ResponseEntity(cargoService.salvar(cargo), HttpStatus.CREATED);
    }
}
