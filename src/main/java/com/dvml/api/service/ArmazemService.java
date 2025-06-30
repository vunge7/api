package com.dvml.api.service;

import com.dvml.api.dto.ArmazemDTO;
import com.dvml.api.entity.Armazem;
import com.dvml.api.repository.ArmazemRepository;
import com.dvml.api.repository.FilialRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArmazemService {

    private static final Logger logger = LoggerFactory.getLogger(ArmazemService.class);

    @Autowired
    private ArmazemRepository armazemRepository;

    @Autowired
    private FilialRepository filialRepository;

    public List<ArmazemDTO> listarTodasArmazem() {
        logger.info("Listando todos os armazéns");
        List<Armazem> armazens = armazemRepository.findAll();
        logger.debug("Total de armazéns encontrados: {}", armazens.size());

        List<ArmazemDTO> dtos = armazens.stream()
                .map(armazem -> {
                    String filialNome;
                    Long filialId = armazem.getFilialId();
                    if (filialId == null) {
                        filialNome = "Sem filial associada (filialId nulo)";
                        logger.warn("Armazém ID {} tem filialId nulo", armazem.getId());
                    } else {
                        filialNome = filialRepository.findById(filialId)
                                .map(filial -> {
                                    logger.debug("Filial encontrada para armazém ID {}: filialId={}, nome={}",
                                            armazem.getId(), filialId, filial.getNome());
                                    return filial.getNome();
                                })
                                .orElseGet(() -> {
                                    logger.warn("Filial não encontrada para armazém ID {}: filialId={}",
                                            armazem.getId(), filialId);
                                    return "Filial não encontrada (ID: " + filialId + ")";
                                });
                    }
                    ArmazemDTO dto = ArmazemDTO.fromEntity(armazem, filialNome);
                    logger.debug("Armazém mapeado para DTO: id={}, designacao={}, filialId={}, filialNome={}",
                            dto.getId(), dto.getDesignacao(), dto.getFilialId(), dto.getFilialNome());
                    return dto;
                })
                .collect(Collectors.toList());
        logger.info("Total de armazéns retornados: {}", dtos.size());
        return dtos;
    }

    public ArmazemDTO getArmazemById(Long id) {
        logger.info("Buscando armazém com ID: {}", id);
        Armazem armazem = armazemRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Armazém com ID {} não encontrado", id);
                    return new IllegalArgumentException("Armazém não encontrado");
                });
        String filialNome = armazem.getFilialId() != null
                ? filialRepository.findById(armazem.getFilialId())
                .map(filial -> {
                    logger.debug("Filial encontrada para armazém ID {}: filialId={}, nome={}",
                            id, armazem.getFilialId(), filial.getNome());
                    return filial.getNome();
                })
                .orElseGet(() -> {
                    logger.warn("Filial não encontrada para armazém ID {}: filialId={}",
                            id, armazem.getFilialId());
                    return "Filial não encontrada (ID: " + armazem.getFilialId() + ")";
                })
                : "Sem filial associada (filialId nulo)";
        ArmazemDTO dto = ArmazemDTO.fromEntity(armazem, filialNome);
        logger.debug("Armazém retornado: id={}, designacao={}, filialId={}, filialNome={}",
                dto.getId(), dto.getDesignacao(), dto.getFilialId(), dto.getFilialNome());
        return dto;
    }

    public ArmazemDTO criar(ArmazemDTO armazemDTO) {
        logger.info("Criando armazém: {}", armazemDTO.getDesignacao());
        if (armazemDTO.getFilialId() == null) {
            logger.error("FilialId é obrigatório para criar armazém: {}", armazemDTO.getDesignacao());
            throw new IllegalArgumentException("FilialId é obrigatório");
        }
        filialRepository.findById(armazemDTO.getFilialId())
                .orElseThrow(() -> {
                    logger.error("Filial com ID {} não encontrada", armazemDTO.getFilialId());
                    return new IllegalArgumentException("Filial não encontrada");
                });
        Armazem armazem = armazemDTO.toEntity();
        Armazem saved = armazemRepository.save(armazem);
        String filialNome = filialRepository.findById(saved.getFilialId())
                .map(filial -> {
                    logger.debug("Filial encontrada para armazém criado ID {}: filialId={}, nome={}",
                            saved.getId(), saved.getFilialId(), filial.getNome());
                    return filial.getNome();
                })
                .orElseGet(() -> {
                    logger.warn("Filial não encontrada para armazém criado ID {}: filialId={}",
                            saved.getId(), saved.getFilialId());
                    return "Filial não encontrada (ID: " + saved.getFilialId() + ")";
                });
        ArmazemDTO dto = ArmazemDTO.fromEntity(saved, filialNome);
        logger.debug("Armazém criado: {}", dto);
        return dto;
    }

    public ArmazemDTO update(ArmazemDTO armazemDTO) {
        logger.info("Atualizando armazém com ID: {}", armazemDTO.getId());
        Armazem armazem = armazemRepository.findById(armazemDTO.getId())
                .orElseThrow(() -> {
                    logger.error("Armazém com ID {} não encontrado", armazemDTO.getId());
                    return new IllegalArgumentException("Armazém não encontrado");
                });
        if (armazemDTO.getFilialId() == null) {
            logger.error("FilialId é obrigatório para atualizar armazém ID: {}", armazemDTO.getId());
            throw new IllegalArgumentException("FilialId é obrigatório");
        }
        filialRepository.findById(armazemDTO.getFilialId())
                .orElseThrow(() -> {
                    logger.error("Filial com ID {} não encontrada", armazemDTO.getFilialId());
                    return new IllegalArgumentException("Filial não encontrada");
                });
        armazem.setDesignacao(armazemDTO.getDesignacao());
        armazem.setFilialId(armazemDTO.getFilialId());
        Armazem updated = armazemRepository.save(armazem);
        String filialNome = filialRepository.findById(updated.getFilialId())
                .map(filial -> {
                    logger.debug("Filial encontrada para armazém atualizado ID {}: filialId={}, nome={}",
                            updated.getId(), updated.getFilialId(), filial.getNome());
                    return filial.getNome();
                })
                .orElseGet(() -> {
                    logger.warn("Filial não encontrada para armazém atualizado ID {}: filialId={}",
                            updated.getId(), updated.getFilialId());
                    return "Filial não encontrada (ID: " + updated.getFilialId() + ")";
                });
        ArmazemDTO dto = ArmazemDTO.fromEntity(updated, filialNome);
        logger.debug("Armazém atualizado: {}", dto);
        return dto;
    }

    public void deleteArmazem(Long id) {
        logger.info("Deletando armazém com ID: {}", id);
        Armazem armazem = armazemRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Armazém com ID {} não encontrado", id);
                    return new IllegalArgumentException("Armazém não encontrado");
                });
        armazemRepository.delete(armazem);
        logger.debug("Armazém com ID {} deletado", id);
    }
}