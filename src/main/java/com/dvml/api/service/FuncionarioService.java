package com.dvml.api.service;

import com.dvml.api.dto.FuncionarioDTO;
import com.dvml.api.dto.LinhaSubsidioDTO;
import com.dvml.api.entity.Funcionario;
import com.dvml.api.repository.FuncionarioRepository;
import com.dvml.api.repository.PessoaRepository;
import com.dvml.api.util.EstadoFuncionario;
import com.dvml.api.util.FechoPeriodo;
import com.dvml.api.util.SegurancaSocial;
import com.dvml.api.util.TipoContrato;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class FuncionarioService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FuncionarioService.class);

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private LinhaSubsidioService linhaSubsidioService;

    // ======================== CRUD PRINCIPAL ========================

    public FuncionarioDTO create(FuncionarioDTO funcionarioDTO) {
        LOGGER.info("Criando funcionário com pessoaId: {}", funcionarioDTO.getPessoaId());
        validateFuncionarioDTO(funcionarioDTO);

        if (!pessoaRepository.existsById(funcionarioDTO.getPessoaId())) {
            throw new EntityNotFoundException("Pessoa não encontrada com ID: " + funcionarioDTO.getPessoaId());
        }

        if (funcionarioRepository.existsByPessoaId(funcionarioDTO.getPessoaId())) {
            throw new IllegalArgumentException("Já existe um funcionário cadastrado para esta pessoa.");
        }

        Funcionario funcionario = toEntity(funcionarioDTO);
        funcionario = funcionarioRepository.save(funcionario);

        if (funcionarioDTO.getSubsidios() != null && !funcionarioDTO.getSubsidios().isEmpty()) {
            linhaSubsidioService.createFromRequest(funcionario.getId(), funcionarioDTO.getSubsidios());
        }

        return findById(funcionario.getId());
    }

    public FuncionarioDTO update(Long id, FuncionarioDTO funcionarioDTO) {
        validateFuncionarioDTO(funcionarioDTO);

        Funcionario funcionario = funcionarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Funcionário não encontrado com ID: " + id));

        if (!pessoaRepository.existsById(funcionarioDTO.getPessoaId())) {
            throw new EntityNotFoundException("Pessoa não encontrada com ID: " + funcionarioDTO.getPessoaId());
        }

        updateEntity(funcionario, funcionarioDTO);
        funcionario = funcionarioRepository.save(funcionario);

        linhaSubsidioService.deleteByFuncionarioId(id);
        if (funcionarioDTO.getSubsidios() != null && !funcionarioDTO.getSubsidios().isEmpty()) {
            linhaSubsidioService.createFromRequest(id, funcionarioDTO.getSubsidios());
        }

        return findById(id);
    }

    public FuncionarioDTO findById(Long id) {
        Funcionario funcionario = funcionarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Funcionário não encontrado com ID: " + id));

        FuncionarioDTO dto = toDTO(funcionario);
        List<LinhaSubsidioDTO> subsidios = linhaSubsidioService.findByFuncionarioId(id);
        dto.setSubsidios(subsidios);
        return dto;
    }

    public List<FuncionarioDTO> findAll() {
        return funcionarioRepository.findAll().stream()
                .map(funcionario -> {
                    FuncionarioDTO dto = toDTO(funcionario);
                    List<LinhaSubsidioDTO> subsidios = linhaSubsidioService.findByFuncionarioId(funcionario.getId());
                    dto.setSubsidios(subsidios);
                    return dto;
                })
                .collect(Collectors.toList());
    }

    public void delete(Long id) {
        if (!funcionarioRepository.existsById(id)) {
            throw new EntityNotFoundException("Funcionário não encontrado com ID: " + id);
        }
        linhaSubsidioService.deleteByFuncionarioId(id);
        funcionarioRepository.deleteById(id);
    }

    public boolean existsByPessoaId(Long pessoaId) {
        return funcionarioRepository.existsByPessoaId(pessoaId);
    }

    // ======================== MÉTODOS PRIVADOS ========================

    private void validateFuncionarioDTO(FuncionarioDTO dto) {
        if (dto == null) throw new IllegalArgumentException("FuncionarioDTO não pode ser nulo.");
        if (dto.getPessoaId() == null) throw new IllegalArgumentException("PessoaId é obrigatório.");
        if (dto.getTipoContrato() == null || dto.getTipoContrato().isEmpty()) throw new IllegalArgumentException("Tipo de contrato é obrigatório.");
        TipoContrato.valueOf(dto.getTipoContrato());
        if (dto.getSalario() == null || dto.getSalario().compareTo(BigDecimal.ZERO) <= 0) throw new IllegalArgumentException("Salário deve ser maior que zero.");
        if (dto.getDataAdmissao() == null) throw new IllegalArgumentException("Data de admissão é obrigatória.");
        if (dto.getDescricao() == null || dto.getDescricao().isEmpty()) throw new IllegalArgumentException("Descrição é obrigatória.");
        if (dto.getCargo() == null || dto.getCargo().isEmpty()) throw new IllegalArgumentException("Cargo é obrigatório.");
        if (dto.getDepartamentoId() == null) throw new IllegalArgumentException("DepartamentoId é obrigatório.");
        FechoPeriodo.valueOf(dto.getFechoPeriodo());
        SegurancaSocial.valueOf(dto.getSegurancaSocial());
        if (dto.getEstadoFuncionario() == null) throw new IllegalArgumentException("Estado do funcionário é obrigatório.");
    }

    private Funcionario toEntity(FuncionarioDTO dto) {
        Funcionario funcionario = new Funcionario();
        funcionario.setId(dto.getId());
        funcionario.setPessoaId(dto.getPessoaId());
        funcionario.setTipoContrato(TipoContrato.valueOf(dto.getTipoContrato()));
        funcionario.setSalario(dto.getSalario());
        funcionario.setDataAdmissao(dto.getDataAdmissao());
        funcionario.setDescricao(dto.getDescricao());
        funcionario.setCargo(dto.getCargo());
        funcionario.setDepartamentoId(dto.getDepartamentoId());
        funcionario.setFechoPeriodo(FechoPeriodo.valueOf(dto.getFechoPeriodo()));
        funcionario.setSegurancaSocial(SegurancaSocial.valueOf(dto.getSegurancaSocial()));
        funcionario.setEstadoFuncionario(dto.getEstadoFuncionario());
        // ✅ CAMPO EMPRESA
        funcionario.setEmpresaId(dto.getEmpresaId());
        return funcionario;
    }

    private FuncionarioDTO toDTO(Funcionario funcionario) {
        FuncionarioDTO dto = new FuncionarioDTO();
        dto.setId(funcionario.getId());
        dto.setPessoaId(funcionario.getPessoaId());
        dto.setTipoContrato(funcionario.getTipoContrato().name());
        dto.setSalario(funcionario.getSalario());
        dto.setDataAdmissao(funcionario.getDataAdmissao());
        dto.setDescricao(funcionario.getDescricao());
        dto.setCargo(funcionario.getCargo());
        dto.setDepartamentoId(funcionario.getDepartamentoId());
        dto.setFechoPeriodo(funcionario.getFechoPeriodo().name());
        dto.setSegurancaSocial(funcionario.getSegurancaSocial().name());
        dto.setEstadoFuncionario(funcionario.getEstadoFuncionario());
        // ✅ CAMPO EMPRESA
        dto.setEmpresaId(funcionario.getEmpresaId());
        return dto;
    }

    private void updateEntity(Funcionario funcionario, FuncionarioDTO dto) {
        funcionario.setPessoaId(dto.getPessoaId());
        funcionario.setTipoContrato(TipoContrato.valueOf(dto.getTipoContrato()));
        funcionario.setSalario(dto.getSalario());
        funcionario.setDataAdmissao(dto.getDataAdmissao());
        funcionario.setDescricao(dto.getDescricao());
        funcionario.setCargo(dto.getCargo());
        funcionario.setDepartamentoId(dto.getDepartamentoId());
        funcionario.setFechoPeriodo(FechoPeriodo.valueOf(dto.getFechoPeriodo()));
        funcionario.setSegurancaSocial(SegurancaSocial.valueOf(dto.getSegurancaSocial()));
        funcionario.setEstadoFuncionario(dto.getEstadoFuncionario());
        // ✅ CAMPO EMPRESA
        funcionario.setEmpresaId(dto.getEmpresaId());
    }
}
