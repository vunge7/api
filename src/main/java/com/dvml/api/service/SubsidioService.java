package com.dvml.api.service;

import com.dvml.api.dto.SubsidioDTO;
import com.dvml.api.entity.Subsidio;
import com.dvml.api.repository.SubsidioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SubsidioService {

    @Autowired
    private SubsidioRepository subsidioRepository;

    // Criar subsídio
    public SubsidioDTO create(SubsidioDTO subsidioDTO) {
        Subsidio subsidio = toEntity(subsidioDTO);
        subsidio = subsidioRepository.save(subsidio);
        return toDTO(subsidio);
    }

    // Atualizar subsídio existente
    public SubsidioDTO update(Long id, SubsidioDTO subsidioDTO) {
        Subsidio subsidio = subsidioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Subsídio não encontrado com ID: " + id));
        updateEntity(subsidio, subsidioDTO);
        subsidio = subsidioRepository.save(subsidio);
        return toDTO(subsidio);
    }

    // Buscar subsídio por ID
    public SubsidioDTO findById(Long id) {
        Subsidio subsidio = subsidioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Subsídio não encontrado com ID: " + id));
        return toDTO(subsidio);
    }

    // Listar todos os subsídios
    public List<SubsidioDTO> findAll() {
        return subsidioRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // Deletar subsídio
    public void delete(Long id) {
        if (!subsidioRepository.existsById(id)) {
            throw new EntityNotFoundException("Subsídio não encontrado com ID: " + id);
        }
        subsidioRepository.deleteById(id);
    }

    // Conversão de DTO para Entity
    private Subsidio toEntity(SubsidioDTO dto) {
        Subsidio subsidio = new Subsidio();
        subsidio.setId(dto.getId());
        subsidio.setDescricao(dto.getDescricao());
        subsidio.setEmpresaId(dto.getEmpresaId()); // <--- empresaId adicionado
        return subsidio;
    }

    // Conversão de Entity para DTO
    private SubsidioDTO toDTO(Subsidio subsidio) {
        SubsidioDTO dto = new SubsidioDTO();
        dto.setId(subsidio.getId());
        dto.setDescricao(subsidio.getDescricao());
        dto.setEmpresaId(subsidio.getEmpresaId()); // <--- empresaId adicionado
        return dto;
    }

    // Atualizar campos da Entity com dados do DTO
    private void updateEntity(Subsidio subsidio, SubsidioDTO dto) {
        subsidio.setDescricao(dto.getDescricao());
        subsidio.setEmpresaId(dto.getEmpresaId()); // <--- empresaId adicionado
    }
}
