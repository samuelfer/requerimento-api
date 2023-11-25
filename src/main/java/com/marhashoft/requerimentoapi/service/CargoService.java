package com.marhashoft.requerimentoapi.service;

import com.marhashoft.requerimentoapi.exception.DataIntegrationViolationApiException;
import com.marhashoft.requerimentoapi.model.Cargo;
import com.marhashoft.requerimentoapi.model.dto.CargoDTO;
import com.marhashoft.requerimentoapi.repository.CargoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CargoService {

    @Autowired
    private CargoRepository cargoRepository;
    @Autowired
    ModelMapper modelMapper;

    public Cargo findByIdOuErro(Long id) {
        return cargoRepository.findById(id).orElseThrow(() -> new RuntimeException("Cargo não encontrado com id " + id));
    }

    public List<Cargo> listarTodos() {
        return cargoRepository.findAll();
    }

    public CargoDTO salvar(CargoDTO cargoDTO) {
        Cargo cargo = cargoDTOToCargo(cargoDTO);
        cargoJaCadastrado(cargo);
        cargo.setAtivo(true);

        return cargoToCargoDTO(cargoRepository.save(cargo));
    }

    private void cargoJaCadastrado(Cargo cargo) {
        Optional<Cargo> cargoExists = cargoRepository.findByDescricao(cargo.getDescricao());

        if (cargoExists.isPresent() && !cargoExists.get().getId().equals(cargo.getId())) {
            throw new DataIntegrationViolationApiException("O cargo  "
                    + cargo.getDescricao() + " já foi cadastrado no sistema!");
        }
    }

    private CargoDTO cargoToCargoDTO(Cargo cargo) {
        return modelMapper.map(cargo, CargoDTO.class);
    }

    private Cargo cargoDTOToCargo(CargoDTO cargoDTO) {
        return modelMapper.map(cargoDTO, Cargo.class);
    }
}
