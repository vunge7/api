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

    public FuncionarioDTO create(FuncionarioDTO funcionarioDTO) {
        LOGGER.info("Criando funcionário com pessoaId: {}", funcionarioDTO.getPessoaId());
        validateFuncionarioDTO(funcionarioDTO);

        if (!pessoaRepository.existsById(funcionarioDTO.getPessoaId())) {
            LOGGER.error("Pessoa não encontrada com ID: {}", funcionarioDTO.getPessoaId());
            throw new EntityNotFoundException("Pessoa não encontrada com ID: " + funcionarioDTO.getPessoaId());
        }

        if (funcionarioRepository.existsByPessoaId(funcionarioDTO.getPessoaId())) {
            LOGGER.error("Funcionário já existe para pessoaId: {}", funcionarioDTO.getPessoaId());
            throw new IllegalArgumentException("Já existe um funcionário cadastrado para esta pessoa.");
        }

        Funcionario funcionario = toEntity(funcionarioDTO);
        funcionario = funcionarioRepository.save(funcionario);
        LOGGER.info("Funcionário criado com sucesso! ID: {}", funcionario.getId());

        if (funcionarioDTO.getSubsidios() != null && !funcionarioDTO.getSubsidios().isEmpty()) {
            LOGGER.info("Criando {} subsídios para funcionário ID: {}", funcionarioDTO.getSubsidios().size(), funcionario.getId());
            try {
                linhaSubsidioService.createFromRequest(funcionario.getId(), funcionarioDTO.getSubsidios());
                LOGGER.info("Subsídios criados com sucesso para funcionário ID: {}", funcionario.getId());
            } catch (Exception e) {
                LOGGER.error("Erro ao criar subsídios para funcionário ID: {}. Mensagem: {}", funcionario.getId(), e.getMessage());
                throw new RuntimeException("Falha ao criar subsídios: " + e.getMessage(), e);
            }
        } else {
            LOGGER.info("Nenhum subsídio fornecido para funcionário ID: {}", funcionario.getId());
        }

        FuncionarioDTO result = findById(funcionario.getId());
        LOGGER.info("Funcionário retornado com sucesso: ID {}", result.getId());
        return result;
    }

    public FuncionarioDTO update(Long id, FuncionarioDTO funcionarioDTO) {
        LOGGER.info("Atualizando funcionário com ID: {}", id);
        validateFuncionarioDTO(funcionarioDTO);

        Funcionario funcionario = funcionarioRepository.findById(id)
                .orElseThrow(() -> {
                    LOGGER.error("Funcionário não encontrado com ID: {}", id);
                    return new EntityNotFoundException("Funcionário não encontrado com ID: " + id);
                });

        if (!pessoaRepository.existsById(funcionarioDTO.getPessoaId())) {
            LOGGER.error("Pessoa não encontrada com ID: {}", funcionarioDTO.getPessoaId());
            throw new EntityNotFoundException("Pessoa não encontrada com ID: " + funcionarioDTO.getPessoaId());
        }

        updateEntity(funcionario, funcionarioDTO);
        funcionario = funcionarioRepository.save(funcionario);
        LOGGER.info("Funcionário atualizado com sucesso! ID: {}", funcionario.getId());

        try {
            linhaSubsidioService.deleteByFuncionarioId(id);
            if (funcionarioDTO.getSubsidios() != null && !funcionarioDTO.getSubsidios().isEmpty()) {
                LOGGER.info("Atualizando {} subsídios para funcionário ID: {}", funcionarioDTO.getSubsidios().size(), id);
                linhaSubsidioService.createFromRequest(id, funcionarioDTO.getSubsidios());
                LOGGER.info("Subsídios atualizados com sucesso para funcionário ID: {}", id);
            } else {
                LOGGER.info("Nenhum subsídio fornecido. Subsídios existentes removidos para funcionário ID: {}", id);
            }
        } catch (Exception e) {
            LOGGER.error("Erro ao atualizar subsídios para funcionário ID: {}. Mensagem: {}", id, e.getMessage());
            throw new RuntimeException("Falha ao atualizar subsídios: " + e.getMessage(), e);
        }

        FuncionarioDTO result = findById(id);
        LOGGER.info("Funcionário retornado com sucesso após atualização: ID {}", result.getId());
        return result;
    }

    public FuncionarioDTO findById(Long id) {
        LOGGER.info("Buscando funcionário com ID: {}", id);
        Funcionario funcionario = funcionarioRepository.findById(id)
                .orElseThrow(() -> {
                    LOGGER.error("Funcionário não encontrado com ID: {}", id);
                    return new EntityNotFoundException("Funcionário não encontrado com ID: " + id);
                });
        FuncionarioDTO dto = toDTO(funcionario);
        List<LinhaSubsidioDTO> subsidios = linhaSubsidioService.findByFuncionarioId(id);
        dto.setSubsidios(subsidios);
        LOGGER.info("Funcionário encontrado com sucesso: ID {}", id);
        return dto;
    }

    public List<FuncionarioDTO> findAll() {
        LOGGER.info("Buscando todos os funcionários");
        List<FuncionarioDTO> result = funcionarioRepository.findAll().stream()
                .map(funcionario -> {
                    FuncionarioDTO dto = toDTO(funcionario);
                    List<LinhaSubsidioDTO> subsidios = linhaSubsidioService.findByFuncionarioId(funcionario.getId());
                    dto.setSubsidios(subsidios);
                    return dto;
                })
                .collect(Collectors.toList());
        LOGGER.info("Encontrados {} funcionários com sucesso", result.size());
        return result;
    }

    public void delete(Long id) {
        LOGGER.info("Deletando funcionário com ID: {}", id);
        if (!funcionarioRepository.existsById(id)) {
            LOGGER.error("Funcionário não encontrado com ID: {}", id);
            throw new EntityNotFoundException("Funcionário não encontrado com ID: " + id);
        }
        linhaSubsidioService.deleteByFuncionarioId(id);
        funcionarioRepository.deleteById(id);
        LOGGER.info("Funcionário deletado com sucesso: ID {}", id);
    }

    public boolean existsByPessoaId(Long pessoaId) {
        LOGGER.info("Verificando se existe funcionário para pessoaId: {}", pessoaId);
        boolean exists = funcionarioRepository.existsByPessoaId(pessoaId);
        LOGGER.info("Funcionário existe para pessoaId {}: {}", pessoaId, exists);
        return exists;
    }

    private void validateFuncionarioDTO(FuncionarioDTO dto) {
        if (dto == null) {
            LOGGER.error("FuncionarioDTO não pode ser nulo");
            throw new IllegalArgumentException("FuncionarioDTO não pode ser nulo.");
        }
        if (dto.getPessoaId() == null) {
            LOGGER.error("PessoaId é obrigatório");
            throw new IllegalArgumentException("PessoaId é obrigatório.");
        }
        if (dto.getTipoDeContrato() == null || dto.getTipoDeContrato().isEmpty()) {
            LOGGER.error("Tipo de contrato é obrigatório");
            throw new IllegalArgumentException("Tipo de contrato é obrigatório.");
        }
        try {
            TipoContrato.valueOf(dto.getTipoDeContrato());
        } catch (IllegalArgumentException e) {
            LOGGER.error("Tipo de contrato inválido: {}", dto.getTipoDeContrato());
            throw new IllegalArgumentException("Tipo de contrato inválido. Valores válidos: " + Arrays.toString(TipoContrato.values()));
        }
        if (dto.getSalario() == null || dto.getSalario().compareTo(BigDecimal.ZERO) <= 0) {
            LOGGER.error("Salário deve ser maior que zero. Valor recebido: {}", dto.getSalario());
            throw new IllegalArgumentException("Salário deve ser maior que zero.");
        }
        if (dto.getDataAdmissao() == null) {
            LOGGER.error("Data de admissão é obrigatória");
            throw new IllegalArgumentException("Data de admissão é obrigatória. Formato esperado: yyyy-MM-dd ou yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        }
        if (dto.getDescricao() == null || dto.getDescricao().isEmpty()) {
            LOGGER.error("Descrição é obrigatória");
            throw new IllegalArgumentException("Descrição é obrigatória.");
        }
        if (dto.getCargo() == null || dto.getCargo().isEmpty()) {
            LOGGER.error("Cargo é obrigatório");
            throw new IllegalArgumentException("Cargo é obrigatório.");
        }
        if (dto.getDepartamentoId() == null) {
            LOGGER.error("DepartamentoId é obrigatório");
            throw new IllegalArgumentException("DepartamentoId é obrigatório.");
        }
        if (dto.getFechoContas() == null || dto.getFechoContas().isEmpty()) {
            LOGGER.error("Fecho de período é obrigatório");
            throw new IllegalArgumentException("Fecho de período é obrigatório.");
        }
        try {
            FechoPeriodo.valueOf(dto.getFechoContas());
        } catch (IllegalArgumentException e) {
            LOGGER.error("Fecho de período inválido: {}", dto.getFechoContas());
            throw new IllegalArgumentException("Fecho de período inválido. Valores válidos: " + Arrays.toString(FechoPeriodo.values()));
        }
        if (dto.getSegurancaSocial() == null || dto.getSegurancaSocial().isEmpty()) {
            LOGGER.error("Segurança social é obrigatória");
            throw new IllegalArgumentException("Segurança social é obrigatória.");
        }
        try {
            SegurancaSocial.valueOf(dto.getSegurancaSocial());
        } catch (IllegalArgumentException e) {
            LOGGER.error("Segurança social inválida: {}", dto.getSegurancaSocial());
            throw new IllegalArgumentException("Segurança social inválida. Valores válidos: " + Arrays.toString(SegurancaSocial.values()));
        }
        if (dto.getEstadoFuncionario() == null) {
            LOGGER.error("Estado do funcionário é obrigatório");
            throw new IllegalArgumentException("Estado do funcionário é obrigatório. Valores válidos: " + Arrays.toString(EstadoFuncionario.values()));
        }
    }

    private Funcionario toEntity(FuncionarioDTO dto) {
        Funcionario funcionario = new Funcionario();
        funcionario.setId(dto.getId());
        funcionario.setPessoaId(dto.getPessoaId());
        funcionario.setTipoContrato(TipoContrato.valueOf(dto.getTipoDeContrato()));
        funcionario.setSalario(dto.getSalario());
        funcionario.setDataAdmissao(dto.getDataAdmissao());
        funcionario.setDescricao(dto.getDescricao());
        funcionario.setCargo(dto.getCargo());
        funcionario.setDepartamentoId(dto.getDepartamentoId());
        funcionario.setFechoPeriodo(FechoPeriodo.valueOf(dto.getFechoContas()));
        funcionario.setSegurancaSocial(SegurancaSocial.valueOf(dto.getSegurancaSocial()));
        funcionario.setEstadoFuncionario(dto.getEstadoFuncionario());
        return funcionario;
    }

    private FuncionarioDTO toDTO(Funcionario funcionario) {
        FuncionarioDTO dto = new FuncionarioDTO();
        dto.setId(funcionario.getId());
        dto.setPessoaId(funcionario.getPessoaId());
        dto.setTipoDeContrato(funcionario.getTipoContrato().name());
        dto.setSalario(funcionario.getSalario());
        dto.setDataAdmissao(funcionario.getDataAdmissao());
        dto.setDescricao(funcionario.getDescricao());
        dto.setCargo(funcionario.getCargo());
        dto.setDepartamentoId(funcionario.getDepartamentoId());
        dto.setFechoContas(funcionario.getFechoPeriodo().name());
        dto.setSegurancaSocial(funcionario.getSegurancaSocial().name());
        dto.setEstadoFuncionario(funcionario.getEstadoFuncionario());
        return dto;
    }

    private void updateEntity(Funcionario funcionario, FuncionarioDTO dto) {
        funcionario.setPessoaId(dto.getPessoaId());
        funcionario.setTipoContrato(TipoContrato.valueOf(dto.getTipoDeContrato()));
        funcionario.setSalario(dto.getSalario());
        funcionario.setDataAdmissao(dto.getDataAdmissao());
        funcionario.setDescricao(dto.getDescricao());
        funcionario.setCargo(dto.getCargo());
        funcionario.setDepartamentoId(dto.getDepartamentoId());
        funcionario.setFechoPeriodo(FechoPeriodo.valueOf(dto.getFechoContas()));
        funcionario.setSegurancaSocial(SegurancaSocial.valueOf(dto.getSegurancaSocial()));
        funcionario.setEstadoFuncionario(dto.getEstadoFuncionario());
    }
}