package com.dvml.api.service;

import com.dvml.api.dto.LinhaSubsidioDTO;
import com.dvml.api.entity.LinhaSubsidio;
import com.dvml.api.repository.FuncionarioRepository;
import com.dvml.api.repository.LinhaSubsidioRepository;
import com.dvml.api.repository.SubsidioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LinhaSubsidioService {

    private static final Logger LOGGER = LoggerFactory.getLogger(LinhaSubsidioService.class);

    @Autowired
    private LinhaSubsidioRepository linhaSubsidioRepository;

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @Autowired
    private SubsidioRepository subsidioRepository;

    @Transactional
    public void createFromRequest(Long funcionarioId, List<LinhaSubsidioDTO> dtos) {
        LOGGER.info("Criando linhas de subsídio para funcionário ID: {}", funcionarioId);

        if (!funcionarioRepository.existsById(funcionarioId)) {
            LOGGER.error("Funcionário com ID {} não encontrado", funcionarioId);
            throw new EntityNotFoundException("Funcionário com ID " + funcionarioId + " não encontrado");
        }

        List<LinhaSubsidio> linhas = dtos.stream().map(dto -> {
            if (!subsidioRepository.existsById(dto.getSubsidioId())) {
                LOGGER.error("Subsídio com ID {} não encontrado", dto.getSubsidioId());
                throw new EntityNotFoundException("Subsídio com ID " + dto.getSubsidioId() + " não encontrado");
            }

            if (dto.getValor() == null || dto.getValor() <= 0) {
                LOGGER.error("Valor inválido para subsídio ID {}: {}", dto.getSubsidioId(), dto.getValor());
                throw new IllegalArgumentException("O valor do subsídio deve ser maior que zero");
            }

            LinhaSubsidio linha = new LinhaSubsidio();
            linha.setSubsidioId(dto.getSubsidioId());
            linha.setFuncionarioId(funcionarioId);
            linha.setValor(BigDecimal.valueOf(dto.getValor()));
            linha.setUsuarioId(dto.getUsuarioId());
            return linha;
        }).collect(Collectors.toList());

        List<LinhaSubsidio> savedLinhas = linhaSubsidioRepository.saveAll(linhas);
        LOGGER.info("Criadas {} linhas de subsídio para funcionário ID: {}", savedLinhas.size(), funcionarioId);
    }

    public List<LinhaSubsidioDTO> findByFuncionarioId(Long funcionarioId) {
        LOGGER.info("Buscando linhas de subsídio para funcionário ID: {}", funcionarioId);
        List<LinhaSubsidio> linhas = linhaSubsidioRepository.findByFuncionarioId(funcionarioId);
        return linhas.stream().map(this::toDTO).collect(Collectors.toList());
    }

    public LinhaSubsidioDTO findById(Long id) {
        LOGGER.info("Buscando linha de subsídio ID: {}", id);
        LinhaSubsidio linha = linhaSubsidioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Linha de subsídio com ID " + id + " não encontrada"));
        return toDTO(linha);
    }

    public List<LinhaSubsidioDTO> findAll() {
        LOGGER.info("Buscando todas as linhas de subsídio");
        List<LinhaSubsidio> linhas = linhaSubsidioRepository.findAll();
        return linhas.stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Transactional
    public LinhaSubsidioDTO update(LinhaSubsidioDTO dto) {
        LOGGER.info("Atualizando linha de subsídio ID: {}", dto.getId());
        LinhaSubsidio linha = linhaSubsidioRepository.findById(dto.getId())
                .orElseThrow(() -> new EntityNotFoundException("Linha de subsídio com ID " + dto.getId() + " não encontrada"));

        if (!subsidioRepository.existsById(dto.getSubsidioId())) {
            LOGGER.error("Subsídio com ID {} não encontrado", dto.getSubsidioId());
            throw new EntityNotFoundException("Subsídio com ID " + dto.getSubsidioId() + " não encontrado");
        }

        if (!funcionarioRepository.existsById(dto.getFuncionarioId())) {
            LOGGER.error("Funcionário com ID {} não encontrado", dto.getFuncionarioId());
            throw new EntityNotFoundException("Funcionário com ID " + dto.getFuncionarioId() + " não encontrado");
        }

        if (dto.getValor() == null || dto.getValor() <= 0) {
            LOGGER.error("Valor inválido para subsídio ID {}: {}", dto.getSubsidioId(), dto.getValor());
            throw new IllegalArgumentException("O valor do subsídio deve ser maior que zero");
        }

        linha.setSubsidioId(dto.getSubsidioId());
        linha.setFuncionarioId(dto.getFuncionarioId());
        linha.setValor(BigDecimal.valueOf(dto.getValor()));
        linha.setUsuarioId(dto.getUsuarioId());

        LinhaSubsidio updatedLinha = linhaSubsidioRepository.save(linha);
        return toDTO(updatedLinha);
    }

    @Transactional
    public void delete(Long id) {
        LOGGER.info("Deletando linha de subsídio ID: {}", id);
        if (!linhaSubsidioRepository.existsById(id)) {
            throw new EntityNotFoundException("Linha de subsídio com ID " + id + " não encontrada");
        }
        linhaSubsidioRepository.deleteById(id);
    }

    @Transactional
    public void deleteByFuncionarioId(Long funcionarioId) {
        LOGGER.info("Deletando todas as linhas de subsídio para funcionário ID: {}", funcionarioId);
        List<LinhaSubsidio> linhas = linhaSubsidioRepository.findByFuncionarioId(funcionarioId);
        if (!linhas.isEmpty()) {
            linhaSubsidioRepository.deleteAll(linhas);
            LOGGER.info("Deletadas {} linhas de subsídio para funcionário ID: {}", linhas.size(), funcionarioId);
        } else {
            LOGGER.info("Nenhuma linha de subsídio encontrada para funcionário ID: {}", funcionarioId);
        }
    }

    private LinhaSubsidioDTO toDTO(LinhaSubsidio linha) {
        LinhaSubsidioDTO dto = new LinhaSubsidioDTO();
        dto.setId(linha.getId());
        dto.setSubsidioId(linha.getSubsidioId());
        dto.setFuncionarioId(linha.getFuncionarioId());
        if (linha.getValor() == null) {
            LOGGER.error("Valor nulo encontrado na linha de subsídio ID: {}", linha.getId());
            throw new IllegalStateException("O valor da linha de subsídio não pode ser nulo");
        }
        dto.setValor(linha.getValor().doubleValue());
        dto.setUsuarioId(linha.getUsuarioId());
        return dto;
    }
}