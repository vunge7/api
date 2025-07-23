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

    private PainelPermissaoDTO convertToDTO(PainelPermissao painelPermissao) {
        PainelPermissaoDTO dto = new PainelPermissaoDTO();
        dto.setId(painelPermissao.getId());
        dto.setDataCriacao(painelPermissao.getDataCriacao());
        dto.setDataActualizacao(painelPermissao.getDataActualizacao());
        dto.setUsuarioIdCriacao(painelPermissao.getUsuarioIdCriacao());
        dto.setUsuarioIdActualizacao(painelPermissao.getUsuarioIdActualizacao());
        dto.setPainelId(painelPermissao.getPainelId());
        dto.setUsuarioId(painelPermissao.getUsuarioId());
        dto.setFilialId(painelPermissao.getFilialId());
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
        painelPermissao.setFilialId(dto.getFilialId());
        return painelPermissao;
    }

    @Transactional
    public PainelPermissaoDTO create(PainelPermissaoDTO dto, Long usuarioIdCriacao) {
        boolean exists = painelPermissaoRepository.existsByUsuarioIdAndPainelIdAndFilialId(
                dto.getUsuarioId(), dto.getPainelId(), dto.getFilialId()
        );

        if (exists) {
            throw new RuntimeException("Permissão já existe para este usuário, painel e filial.");
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
                .orElseThrow(() -> new RuntimeException("PainelPermissao not found with id: " + id));
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
    public List<PainelPermissaoDTO> findByUsuarioIdAndFilialId(Long usuarioId, Long filialId) {
        return painelPermissaoRepository.findByUsuarioIdAndFilialId(usuarioId, filialId)
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<Long> findFiliaisByUsuarioId(Long usuarioId) {
        return painelPermissaoRepository.findByUsuarioId(usuarioId)
                .stream()
                .map(PainelPermissao::getFilialId)
                .distinct()
                .collect(Collectors.toList());
    }

    @Transactional
    public PainelPermissaoDTO update(Long id, PainelPermissaoDTO dto, Long usuarioIdActualizacao) {
        PainelPermissao existing = painelPermissaoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("PainelPermissao not found with id: " + id));

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
            throw new RuntimeException("PainelPermissao not found with id: " + id);
        }
        painelPermissaoRepository.deleteById(id);
    }
}
