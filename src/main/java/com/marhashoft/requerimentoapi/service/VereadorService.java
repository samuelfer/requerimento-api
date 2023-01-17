package com.marhashoft.requerimentoapi.service;

import com.marhashoft.requerimentoapi.CargoEnum;
import com.marhashoft.requerimentoapi.exception.DataIntegrationViolationApiException;
import com.marhashoft.requerimentoapi.model.Cargo;
import com.marhashoft.requerimentoapi.model.Vereador;
import com.marhashoft.requerimentoapi.model.dto.VereadorDTO;
import com.marhashoft.requerimentoapi.repository.VereadorRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VereadorService {

    @Autowired
    private VereadorRepository vereadorRepository;
    @Autowired
    private CargoService cargoService;
    @Autowired
    private ModelMapper modelMapper;

    public Vereador findByIdOuErro(Long id) {
        return vereadorRepository.findById(id).orElseThrow(() -> new RuntimeException("Vereador não encontrado com id " + id));
    }

    public List<Vereador> listarTodos() {
        return vereadorRepository.findAll(Sort.by(Sort.Direction.DESC, "nome"));
    }

    public Vereador salvar(VereadorDTO vereadorDTO) {
        vereadorJaCadastrado(vereadorDTO);
        vereadorDTO.setAtivo(true);
        if (vereadorDTO.getCargo().getDescricao() == null) {
            Cargo cargo = cargoService.findByIdOuErro(CargoEnum.VEREADOR.getId());
            vereadorDTO.setCargo(cargo);
        }
        Vereador vereador = modelMapper.map(vereadorDTO, Vereador.class);
        return vereadorRepository.save(vereador);
    }

    public Vereador atualizar(VereadorDTO vereadorDTO) {
        vereadorJaCadastrado(vereadorDTO);
        Vereador vereador = modelMapper.map(vereadorDTO, Vereador.class);
        return vereadorRepository.save(vereador);
    }

    private void vereadorJaCadastrado(VereadorDTO vereadorDTO) {
        Optional<Vereador> servidorExists = vereadorRepository.findByNome(vereadorDTO.getNome());

        if (servidorExists.isPresent() && !servidorExists.get().getId().equals(vereadorDTO.getId())) {
            throw new DataIntegrationViolationApiException("O vereador  "
                    + vereadorDTO.getNome() + " já foi cadastrado no sistema!");
        }
    }
}
