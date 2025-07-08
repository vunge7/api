package com.dvml.api.service;

import com.dvml.api.dto.DepartamentoDTO;
import com.dvml.api.entity.Departamento;
import com.dvml.api.repository.DepartamentoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class DepartamentoService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DepartamentoService.class);

    @Autowired
    private DepartamentoRepository departamentoRepository;

    public DepartamentoDTO create(DepartamentoDTO departamentoDTO) {
        LOGGER.info("Criando departamento com descrição: {}", departamentoDTO.getDescricao());
        validateDepartamentoDTO(departamentoDTO);

        if (departamentoRepository.existsByDescricao(departamentoDTO.getDescricao())) {
            LOGGER.error("Departamento já existe com descrição: {}", departamentoDTO.getDescricao());
            throw new IllegalArgumentException("Já existe um departamento com esta descrição.");
        }

        Departamento departamento = toEntity(departamentoDTO);
        departamento = departamentoRepository.save(departamento);
        LOGGER.info("Departamento criado com sucesso! ID: {}", departamento.getId());

        return toDTO(departamento);
    }

    public DepartamentoDTO update(Long id, DepartamentoDTO departamentoDTO) {
        LOGGER.info("Atualizando departamento com ID: {}", id);
        validateDepartamentoDTO(departamentoDTO);

        Departamento departamento = departamentoRepository.findById(id)
                .orElseThrow(() -> {
                    LOGGER.error("Departamento não encontrado com ID: {}", id);
                    return new EntityNotFoundException("Departamento não encontrado com ID: " + id);
                });

        if (!departamento.getDescricao().equals(departamentoDTO.getDescricao()) &&
                departamentoRepository.existsByDescricao(departamentoDTO.getDescricao())) {
            LOGGER.error("Departamento já existe com descrição: {}", departamentoDTO.getDescricao());
            throw new IllegalArgumentException("Já existe um departamento com esta descrição.");
        }

        updateEntity(departamento, departamentoDTO);
        departamento = departamentoRepository.save(departamento);
        LOGGER.info("Departamento atualizado com sucesso! ID: {}", departamento.getId());

        return toDTO(departamento);
    }

    public DepartamentoDTO findById(Long id) {
        LOGGER.info("Buscando departamento com ID: {}", id);
        Departamento departamento = departamentoRepository.findById(id)
                .orElseThrow(() -> {
                    LOGGER.error("Departamento não encontrado com ID: {}", id);
                    return new EntityNotFoundException("Departamento não encontrado com ID: " + id);
                });
        LOGGER.info("Departamento encontrado com sucesso: ID {}", id);
        return toDTO(departamento);
    }

    public List<DepartamentoDTO> findAll() {
        LOGGER.info("Buscando todos os departamentos");
        List<DepartamentoDTO> result = departamentoRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
        LOGGER.info("Encontrados {} departamentos com sucesso", result.size());
        return result;
    }

    public void delete(Long id) {
        LOGGER.info("Deletando departamento com ID: {}", id);
        if (!departamentoRepository.existsById(id)) {
            LOGGER.error("Departamento não encontrado com ID: {}", id);
            throw new EntityNotFoundException("Departamento não encontrado com ID: " + id);
        }
        departamentoRepository.deleteById(id);
        LOGGER.info("Departamento deletado com sucesso: ID {}", id);
    }

    private void validateDepartamentoDTO(DepartamentoDTO dto) {
        if (dto == null) {
            LOGGER.error("DepartamentoDTO não pode ser nulo");
            throw new IllegalArgumentException("DepartamentoDTO não pode ser nulo.");
        }
        if (dto.getDescricao() == null || dto.getDescricao().isEmpty()) {
            LOGGER.error("Descrição é obrigatória");
            throw new IllegalArgumentException("Descrição é obrigatória.");
        }
    }

    private Departamento toEntity(DepartamentoDTO dto) {
        Departamento departamento = new Departamento();
        departamento.setId(dto.getId());
        departamento.setDescricao(dto.getDescricao());
        return departamento;
    }

    private DepartamentoDTO toDTO(Departamento departamento) {
        DepartamentoDTO dto = new DepartamentoDTO();
        dto.setId(departamento.getId());
        dto.setDescricao(departamento.getDescricao());
        return dto;
    }

    private void updateEntity(Departamento departamento, DepartamentoDTO dto) {
        departamento.setDescricao(dto.getDescricao());
    }
}