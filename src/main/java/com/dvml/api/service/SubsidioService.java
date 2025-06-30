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

    public SubsidioDTO create(SubsidioDTO subsidioDTO) {
        Subsidio subsidio = toEntity(subsidioDTO);
        subsidio = subsidioRepository.save(subsidio);
        return toDTO(subsidio);
    }

    public SubsidioDTO update(Long id, SubsidioDTO subsidioDTO) {
        Subsidio subsidio = subsidioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Subsídio não encontrado com ID: " + id));
        updateEntity(subsidio, subsidioDTO);
        subsidio = subsidioRepository.save(subsidio);
        return toDTO(subsidio);
    }

    public SubsidioDTO findById(Long id) {
        Subsidio subsidio = subsidioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Subsídio não encontrado com ID: " + id));
        return toDTO(subsidio);
    }

    public List<SubsidioDTO> findAll() {
        return subsidioRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public void delete(Long id) {
        if (!subsidioRepository.existsById(id)) {
            throw new EntityNotFoundException("Subsídio não encontrado com ID: " + id);
        }
        subsidioRepository.deleteById(id);
    }

    private Subsidio toEntity(SubsidioDTO dto) {
        Subsidio subsidio = new Subsidio();
        subsidio.setId(dto.getId());
        subsidio.setDescricao(dto.getDescricao());
        return subsidio;
    }

    private SubsidioDTO toDTO(Subsidio subsidio) {
        SubsidioDTO dto = new SubsidioDTO();
        dto.setId(subsidio.getId());
        dto.setDescricao(subsidio.getDescricao());
        return dto;
    }

    private void updateEntity(Subsidio subsidio, SubsidioDTO dto) {
        subsidio.setDescricao(dto.getDescricao());
    }
}