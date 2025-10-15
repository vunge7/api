package com.dvml.api.service;

import com.dvml.api.dto.FilialDTO;
import com.dvml.api.entity.Filial;
import com.dvml.api.repository.FilialRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FilialService {

    private static final Logger logger = LoggerFactory.getLogger(FilialService.class);

    @Autowired
    private FilialRepository repository;

    /**
     * Lista todas as filiais
     */
    public List<FilialDTO> getAllFilials() {
        logger.info("Buscando todas as filiais");
        return repository.findAll().stream()
                .map(FilialDTO::fromEntity)
                .collect(Collectors.toList());
    }

    /**
     * Busca filial pelo ID
     */
    public FilialDTO getFilialById(Long id) {
        logger.info("Buscando filial com ID: {}", id);
        Filial filial = repository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Filial com ID {} não encontrada", id);
                    return new IllegalArgumentException("Filial não encontrada");
                });
        return FilialDTO.fromEntity(filial);
    }

    /**
     * Lista apenas os nomes das filiais
     */
    public List<FilialDTO> listarNomesFiliais() {
        logger.info("Listando nomes das filiais");
        return repository.findAll().stream()
                .map(filial -> {
                    FilialDTO dto = new FilialDTO();
                    dto.setId(filial.getId());
                    dto.setNome(filial.getNome());
                    dto.setEmpresaId(filial.getEmpresaId());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    /**
     * Cria uma nova filial
     */
    public FilialDTO create(FilialDTO filialDTO) {
        logger.info("Criando filial: {}", filialDTO.getNome());
        Filial filial = filialDTO.toEntity();
        Filial saved = repository.save(filial);
        logger.info("Filial criada com sucesso: ID {}", saved.getId());
        return FilialDTO.fromEntity(saved);
    }

    /**
     * Atualiza filial existente
     */
    public FilialDTO update(FilialDTO filialDTO) {
        logger.info("Atualizando filial com ID: {}", filialDTO.getId());
        Filial filial = repository.findById(filialDTO.getId())
                .orElseThrow(() -> {
                    logger.error("Filial com ID {} não encontrada", filialDTO.getId());
                    return new IllegalArgumentException("Filial não encontrada");
                });
        filial.setNome(filialDTO.getNome());
        filial.setNif(filialDTO.getNif());
        filial.setProvincia(filialDTO.getProvincia());
        filial.setMunicipio(filialDTO.getMunicipio());
        filial.setStatus(filialDTO.isStatus());
        filial.setEmpresaId(filialDTO.getEmpresaId()); // atualizado
        Filial updated = repository.save(filial);
        logger.info("Filial atualizada com sucesso: ID {}", updated.getId());
        return FilialDTO.fromEntity(updated);
    }

    /**
     * Deleta filial pelo ID
     */
    public void delete(Long id) {
        logger.info("Deletando filial com ID: {}", id);
        Filial filial = repository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Filial com ID {} não encontrada", id);
                    return new IllegalArgumentException("Filial não encontrada");
                });
        repository.delete(filial);
        logger.info("Filial deletada com sucesso: ID {}", id);
    }
}
