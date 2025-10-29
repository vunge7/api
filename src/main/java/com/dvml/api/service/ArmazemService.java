package com.dvml.api.service;

import com.dvml.api.dto.ArmazemDTO;
import com.dvml.api.entity.Armazem;
import com.dvml.api.repository.ArmazemRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArmazemService {

    private static final Logger logger = LoggerFactory.getLogger(ArmazemService.class);

    @Autowired
    private ArmazemRepository armazemRepository;

    public List<ArmazemDTO> listarTodasArmazem() {
        logger.info("Listando todos os armazéns");
        return armazemRepository.findAll().stream()
                .map(ArmazemDTO::fromEntity)
                .peek(dto -> logger.debug("Armazém listado: id={}, designacao={}, empresaId={}",
                        dto.getId(), dto.getDesignacao(), dto.getEmpresaId()))
                .toList();
    }

    public ArmazemDTO getArmazemById(Long id) {
        logger.info("Buscando armazém com ID: {}", id);
        Armazem armazem = armazemRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Armazém com ID {} não encontrado", id);
                    return new IllegalArgumentException("Armazém não encontrado com ID: " + id);
                });

        ArmazemDTO dto = ArmazemDTO.fromEntity(armazem);
        logger.debug("Armazém retornado: id={}, designacao={}, empresaId={}",
                dto.getId(), dto.getDesignacao(), dto.getEmpresaId());
        return dto;
    }

    public ArmazemDTO criar(ArmazemDTO armazemDTO) {
        logger.info("Criando novo armazém: {}", armazemDTO.getDesignacao());

        validarDesignacao(armazemDTO.getDesignacao());

        Armazem armazem = armazemDTO.toEntity();
        Armazem saved = armazemRepository.save(armazem);

        ArmazemDTO dto = ArmazemDTO.fromEntity(saved);
        logger.info("Armazém criado com sucesso: ID {}", saved.getId());
        return dto;
    }

    public ArmazemDTO update(ArmazemDTO armazemDTO) {
        Long id = armazemDTO.getId();
        if (id == null) {
            logger.error("Tentativa de atualização sem ID");
            throw new IllegalArgumentException("ID do armazém é obrigatório para atualização");
        }

        logger.info("Atualizando armazém com ID: {}", id);

        Armazem armazem = armazemRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Armazém com ID {} não encontrado para atualização", id);
                    return new IllegalArgumentException("Armazém não encontrado com ID: " + id);
                });

        String novaDesignacao = armazemDTO.getDesignacao();
        validarDesignacao(novaDesignacao);

        armazem.setDesignacao(novaDesignacao.trim());
        armazem.setEmpresaId(armazemDTO.getEmpresaId());

        if (armazemDTO.getEmpresaId() == null) {
            logger.warn("Armazém ID {} atualizado sem empresaId (pode ser intencional)", id);
        }

        Armazem updated = armazemRepository.save(armazem);
        ArmazemDTO dto = ArmazemDTO.fromEntity(updated);

        logger.info("Armazém atualizado com sucesso: ID {}", updated.getId());
        return dto;
    }

    public void deleteArmazem(Long id) {
        logger.info("Deletando armazém com ID: {}", id);
        if (!armazemRepository.existsById(id)) {
            logger.error("Tentativa de deletar armazém inexistente: ID {}", id);
            throw new IllegalArgumentException("Armazém não encontrado com ID: " + id);
        }
        armazemRepository.deleteById(id);
        logger.info("Armazém deletado com sucesso: ID {}", id);
    }

    // Validação centralizada
    private void validarDesignacao(String designacao) {
        if (designacao == null || designacao.trim().isEmpty()) {
            throw new IllegalArgumentException("Designação é obrigatória");
        }
        String trimmed = designacao.trim();
        if (trimmed.length() < 3 || trimmed.length() > 100) {
            throw new IllegalArgumentException("Designação deve ter entre 3 e 100 caracteres");
        }
    }
}