package com.dvml.api.service;

import com.dvml.api.dto.PainelDTO;
import com.dvml.api.entity.Painel;
import com.dvml.api.repository.PainelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PainelService {

    @Autowired
    private PainelRepository repository;

    public PainelDTO create(PainelDTO dto) {
        Painel entity = new Painel();
        entity.setDescricao(dto.getDescricao());
        return toDTO(repository.save(entity));
    }

    public List<PainelDTO> findAll() {
        return repository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public PainelDTO findById(Long id) {
        Painel entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Painel não encontrado"));
        return toDTO(entity);
    }

    public PainelDTO update(Long id, PainelDTO dto) {
        Painel entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Painel não encontrado"));
        entity.setDescricao(dto.getDescricao());
        return toDTO(repository.save(entity));
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Painel não encontrado");
        }
        repository.deleteById(id);
    }

    private PainelDTO toDTO(Painel entity) {
        PainelDTO dto = new PainelDTO();
        dto.setId(entity.getId());
        dto.setDescricao(entity.getDescricao());
        return dto;
    }
}