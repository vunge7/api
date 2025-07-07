package com.dvml.api.service;

import com.dvml.api.dto.TipoExameDTO;
import com.dvml.api.entity.TipoExame;
import com.dvml.api.repository.TipoExameRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TipoExameService {
    private static final Logger logger = LoggerFactory.getLogger(TipoExameService.class);

    @Autowired
    private TipoExameRepository repository;

    public List<TipoExameDTO> listarTodos() {
        logger.info("Listando todos os tipos de exame");
        List<TipoExameDTO> dtos = repository.findAll().stream()
                .map(TipoExameDTO::fromEntity)
                .collect(Collectors.toList());
        logger.debug("Total de tipos de exame encontrados: {}", dtos.size());
        return dtos;
    }

    public TipoExameDTO getById(Long id) {
        logger.info("Buscando tipo de exame com ID: {}", id);
        TipoExame tipoExame = repository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Tipo de exame com ID {} não encontrado", id);
                    return new IllegalArgumentException("Tipo de exame não encontrado");
                });
        return TipoExameDTO.fromEntity(tipoExame);
    }

    @Transactional
    public TipoExameDTO criar(TipoExameDTO dto) {
        logger.info("Criando tipo de exame: {}", dto.getNome());
        if (repository.existsByNomeIgnoreCase(dto.getNome())) {
            logger.error("Tipo de exame com nome {} já existe", dto.getNome());
            throw new IllegalArgumentException("Tipo de exame com este nome já existe");
        }
        TipoExame tipoExame = dto.toEntity();
        TipoExame saved = repository.save(tipoExame);
        logger.debug("Tipo de exame criado: {}", saved.getNome());
        return TipoExameDTO.fromEntity(saved);
    }

    @Transactional
    public TipoExameDTO atualizar(TipoExameDTO dto) {
        logger.info("Atualizando tipo de exame com ID: {}", dto.getId());
        TipoExame tipoExame = repository.findById(dto.getId())
                .orElseThrow(() -> {
                    logger.error("Tipo de exame com ID {} não encontrado", dto.getId());
                    return new IllegalArgumentException("Tipo de exame não encontrado");
                });
        if (!tipoExame.getNome().equalsIgnoreCase(dto.getNome()) && repository.existsByNomeIgnoreCase(dto.getNome())) {
            logger.error("Tipo de exame com nome {} já existe", dto.getNome());
            throw new IllegalArgumentException("Tipo de exame com este nome já existe");
        }
        tipoExame.setNome(dto.getNome());
        tipoExame.setDescricao(dto.getDescricao());
        tipoExame.setTipoAmostra(dto.getTipoAmostra());
        TipoExame updated = repository.save(tipoExame);
        logger.debug("Tipo de exame atualizado: {}", updated.getNome());
        return TipoExameDTO.fromEntity(updated);
    }

    @Transactional
    public void deletar(Long id) {
        logger.info("Deletando tipo de exame com ID: {}", id);
        if (!repository.existsById(id)) {
            logger.error("Tipo de exame com ID {} não encontrado", id);
            throw new IllegalArgumentException("Tipo de exame não encontrado");
        }
        repository.deleteById(id);
        logger.debug("Tipo de exame com ID {} deletado", id);
    }
}