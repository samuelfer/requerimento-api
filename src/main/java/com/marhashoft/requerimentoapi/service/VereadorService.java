package com.marhashoft.requerimentoapi.service;

import com.marhashoft.requerimentoapi.CargoEnum;
import com.marhashoft.requerimentoapi.TipoPessoaEnum;
import com.marhashoft.requerimentoapi.exception.DataIntegrationViolationApiException;
import com.marhashoft.requerimentoapi.model.Assessor;
import com.marhashoft.requerimentoapi.model.Cargo;
import com.marhashoft.requerimentoapi.model.Usuario;
import com.marhashoft.requerimentoapi.model.Vereador;
import com.marhashoft.requerimentoapi.model.dto.VereadorDTO;
import com.marhashoft.requerimentoapi.repository.VereadorRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class VereadorService {

    @Autowired
    private VereadorRepository vereadorRepository;
    @Autowired
    private CargoService cargoService;
    @Autowired
    private TipoPessoaService tipoPessoaService;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private AssessorService assessorService;

    public Vereador findByIdOuErro(Long id) {
        return vereadorRepository.findById(id).orElseThrow(() -> new RuntimeException("Vereador não encontrado com id " + id));
    }

    public List<Vereador> listarTodos() {
        Usuario usuario = usuarioService.getusuarioLogadoOuErro();

        if (usuario.getTipoPessoa().getId().equals(TipoPessoaEnum.TIPO_ASSESSOR.getId())) {
            return listarVereadorPorAssessor(usuario.getPessoaId());
        }
        return vereadorRepository.findAll(Sort.by(Sort.Direction.DESC, "nome"));
    }

    public Vereador salvar(VereadorDTO vereadorDTO) {
        vereadorJaCadastrado(vereadorDTO);
        vereadorDTO.setAtivo(true);
        if (vereadorDTO.getCargo().getDescricao() == null) {
            Cargo cargo = cargoService.findByIdOuErro(CargoEnum.VEREADOR.getId());
            vereadorDTO.setTipoPessoa(tipoPessoaService.findByIdOuErro(TipoPessoaService.TIPO_VEREADOR));
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

    private List<Vereador> listarVereadorPorAssessor(Long assessorId) {
        Assessor assessor = assessorService.findByIdOuErro(assessorId);
        List<Vereador> vereador = new ArrayList<>();
        Optional<Vereador> vereadorResult = vereadorRepository.findById(assessor.getVereador().getId());
        if (!vereadorResult.isPresent()) {
            throw new RuntimeException("Erro ao tentar listar o vereador");
        }
        vereador.add(vereadorResult.get());
        return vereador;
    }
}
