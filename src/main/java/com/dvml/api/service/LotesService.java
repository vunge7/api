package com.dvml.api.service;

import com.dvml.api.dto.LotesDTO;
import com.dvml.api.entity.Lotes;
import com.dvml.api.repository.LotesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LotesService {

    private static final Logger logger = LoggerFactory.getLogger(LotesService.class);

    @Autowired
    private LotesRepository lotesRepository;

    public LotesDTO save(LotesDTO dto) {
        logger.info("Salvando lote: {}", dto.getDesignacao());
        if (dto.getUsuarioId() == null || dto.getDesignacao() == null || dto.getDataCriacao() == null || dto.getDataVencimento() == null) {
            throw new IllegalArgumentException("Campos obrigatórios não preenchidos.");
        }
        Lotes entity = dto.toEntity();
        Lotes savedEntity = lotesRepository.save(entity);
        return LotesDTO.fromEntity(savedEntity);
    }

    public List<LotesDTO> findAll() {
        logger.info("Listando todos os lotes");
        return lotesRepository.findAll().stream()
                .map(LotesDTO::fromEntity)
                .collect(Collectors.toList());
    }

    public LotesDTO findById(Long id) {
        logger.info("Buscando lote com ID: {}", id);
        Optional<Lotes> entity = lotesRepository.findById(id);
        if (entity.isEmpty()) {
            throw new IllegalArgumentException("Lote não encontrado com ID: " + id);
        }
        return LotesDTO.fromEntity(entity.get());
    }

    public LotesDTO update(Long id, LotesDTO dto) {
        logger.info("Atualizando lote com ID: {}", id);
        if (!lotesRepository.existsById(id)) {
            throw new IllegalArgumentException("Lote não encontrado com ID: " + id);
        }
        dto.setId(id);
        Lotes entity = dto.toEntity();
        Lotes updatedEntity = lotesRepository.save(entity);
        return LotesDTO.fromEntity(updatedEntity);
    }

    public void delete(Long id) {
        logger.info("Deletando lote com ID: {}", id);
        if (!lotesRepository.existsById(id)) {
            throw new IllegalArgumentException("Lote não encontrado com ID: " + id);
        }
        lotesRepository.deleteById(id);
    }
}