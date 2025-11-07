package com.dvml.api.service;

import com.dvml.api.dto.PainelPermissaoDTO;
import com.dvml.api.entity.PainelPermissao;
import com.dvml.api.repository.PainelPermissaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PainelPermissaoService {

    @Autowired
    private PainelPermissaoRepository painelPermissaoRepository;

    // -------------------- CONVERSÕES --------------------

    private PainelPermissaoDTO convertToDTO(PainelPermissao painelPermissao) {
        PainelPermissaoDTO dto = new PainelPermissaoDTO();
        dto.setId(painelPermissao.getId());
        dto.setDataCriacao(painelPermissao.getDataCriacao());
        dto.setDataActualizacao(painelPermissao.getDataActualizacao());
        dto.setUsuarioIdCriacao(painelPermissao.getUsuarioIdCriacao());
        dto.setUsuarioIdActualizacao(painelPermissao.getUsuarioIdActualizacao());
        dto.setPainelId(painelPermissao.getPainelId());
        dto.setUsuarioId(painelPermissao.getUsuarioId());
        dto.setEmpresaId(painelPermissao.getEmpresaId());
        return dto;
    }

    private PainelPermissao convertToEntity(PainelPermissaoDTO dto) {
        PainelPermissao painelPermissao = new PainelPermissao();
        painelPermissao.setId(dto.getId());
        painelPermissao.setDataCriacao(dto.getDataCriacao());
        painelPermissao.setDataActualizacao(dto.getDataActualizacao());
        painelPermissao.setUsuarioIdCriacao(dto.getUsuarioIdCriacao());
        painelPermissao.setUsuarioIdActualizacao(dto.getUsuarioIdActualizacao());
        painelPermissao.setPainelId(dto.getPainelId());
        painelPermissao.setUsuarioId(dto.getUsuarioId());
        painelPermissao.setEmpresaId(dto.getEmpresaId());
        return painelPermissao;
    }

    // -------------------- CRUD --------------------

    @Transactional
    public PainelPermissaoDTO create(PainelPermissaoDTO dto, Long usuarioIdCriacao) {
        boolean exists = painelPermissaoRepository.existsByUsuarioIdAndPainelIdAndEmpresaId(
                dto.getUsuarioId(), dto.getPainelId(), dto.getEmpresaId()
        );

        if (exists) {
            throw new RuntimeException("Permissão já existe para este usuário, painel e empresa.");
        }

        PainelPermissao painelPermissao = convertToEntity(dto);
        painelPermissao.setDataCriacao(LocalDateTime.now());
        painelPermissao.setUsuarioIdCriacao(usuarioIdCriacao);

        PainelPermissao saved = painelPermissaoRepository.save(painelPermissao);
        return convertToDTO(saved);
    }

    @Transactional(readOnly = true)
    public PainelPermissaoDTO findById(Long id) {
        return painelPermissaoRepository.findById(id)
                .map(this::convertToDTO)
                .orElseThrow(() -> new RuntimeException("PainelPermissao não encontrado com ID: " + id));
    }

    @Transactional(readOnly = true)
    public List<PainelPermissaoDTO> findAll() {
        return painelPermissaoRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<PainelPermissaoDTO> findByUsuarioId(Long usuarioId) {
        return painelPermissaoRepository.findByUsuarioId(usuarioId)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<PainelPermissaoDTO> findByUsuarioIdAndEmpresaId(Long usuarioId, Long empresaId) {
        return painelPermissaoRepository.findByUsuarioIdAndEmpresaId(usuarioId, empresaId)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Retorna a lista de IDs de empresas distintas associadas a um determinado usuário
     * usando query nativa diretamente do banco.
     */
    @Transactional(readOnly = true)
    public List<Long> findEmpresasByUsuarioId(Long usuarioId) {
        return painelPermissaoRepository.findDistinctEmpresaIdsByUsuarioId(usuarioId);
    }

    @Transactional
    public PainelPermissaoDTO update(Long id, PainelPermissaoDTO dto, Long usuarioIdActualizacao) {
        PainelPermissao existing = painelPermissaoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("PainelPermissao não encontrado com ID: " + id));

        PainelPermissao updated = convertToEntity(dto);
        updated.setId(id);
        updated.setDataCriacao(existing.getDataCriacao());
        updated.setUsuarioIdCriacao(existing.getUsuarioIdCriacao());
        updated.setDataActualizacao(LocalDateTime.now());
        updated.setUsuarioIdActualizacao(usuarioIdActualizacao);

        PainelPermissao saved = painelPermissaoRepository.save(updated);
        return convertToDTO(saved);
    }

    @Transactional
    public void delete(Long id) {
        if (!painelPermissaoRepository.existsById(id)) {
            throw new RuntimeException("PainelPermissao não encontrado com ID: " + id);
        }
        painelPermissaoRepository.deleteById(id);
    }
}
