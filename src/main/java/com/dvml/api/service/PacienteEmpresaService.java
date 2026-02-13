package com.dvml.api.service;

import com.dvml.api.dto.PacienteEmpresaDTO;
import com.dvml.api.entity.PacienteEmpresa;
import com.dvml.api.repository.PacienteEmpresaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PacienteEmpresaService {

    @Autowired
    private PacienteEmpresaRepository repository;

    private PacienteEmpresaDTO toDTO(PacienteEmpresa entity) {
        if (entity == null) return null;

        PacienteEmpresaDTO dto = new PacienteEmpresaDTO();
        dto.setId(entity.getId());
        dto.setEmpresaId(entity.getEmpresaId());
        dto.setPacienteId(entity.getPacienteId());
        return dto;
    }

    private PacienteEmpresa toEntity(PacienteEmpresaDTO dto) {
        if (dto == null) return null;

        PacienteEmpresa entity = new PacienteEmpresa();
        entity.setId(dto.getId());
        entity.setEmpresaId(dto.getEmpresaId());
        entity.setPacienteId(dto.getPacienteId());
        return entity;
    }

    public PacienteEmpresaDTO getById(Long id) {
        PacienteEmpresa entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vínculo não encontrado com ID: " + id));
        return toDTO(entity);
    }

    public List<PacienteEmpresaDTO> listarTodos() {
        return repository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public PacienteEmpresa adicionar(PacienteEmpresa entity) {
        return repository.save(entity);
    }

    public PacienteEmpresa update(PacienteEmpresa entity) {
        if (entity.getId() == null || !repository.existsById(entity.getId())) {
            throw new RuntimeException("Vínculo não encontrado com ID: " + entity.getId());
        }
        return repository.save(entity);
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Vínculo não encontrado com ID: " + id);
        }
        repository.deleteById(id);
    }

    // Métodos para o controller (usando DTO)
    public PacienteEmpresaDTO criar(PacienteEmpresaDTO dto) {
        PacienteEmpresa entity = toEntity(dto);
        entity = adicionar(entity);
        return toDTO(entity);
    }

    public PacienteEmpresaDTO atualizar(PacienteEmpresaDTO dto) {
        PacienteEmpresa entity = toEntity(dto);
        entity = update(entity);
        return toDTO(entity);
    }
}