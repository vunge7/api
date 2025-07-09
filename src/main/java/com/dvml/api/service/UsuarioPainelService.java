package com.dvml.api.service;

import com.dvml.api.dto.PainelDTO;
import com.dvml.api.dto.UsuarioPainelDTO;
import com.dvml.api.entity.Painel;
import com.dvml.api.entity.UsuarioPainel;
import com.dvml.api.repository.UsuarioPainelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioPainelService {

    @Autowired
    private UsuarioPainelRepository usuarioPainelRepository;

    public UsuarioPainelDTO create(UsuarioPainelDTO dto) {
        UsuarioPainel entity = new UsuarioPainel();
        entity.setPainelId(dto.getPainelId());
        entity.setUsuarioCadastradoId(dto.getUsuarioCadastradoId());
        entity.setUsuarioId(dto.getUsuarioId());
        entity.setDataRegistro(new Date());
        entity = usuarioPainelRepository.save(entity);
        return toDTO(entity);
    }

    public List<UsuarioPainelDTO> findAll() {
        return usuarioPainelRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public UsuarioPainelDTO findById(Long id) {
        UsuarioPainel entity = usuarioPainelRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vínculo não encontrado"));
        return toDTO(entity);
    }

    public void delete(Long id) {
        if (!usuarioPainelRepository.existsById(id)) {
            throw new RuntimeException("Vínculo não encontrado");
        }
        usuarioPainelRepository.deleteById(id);
    }

    public UsuarioPainelDTO update(Long id, UsuarioPainelDTO dto) {
        UsuarioPainel entity = usuarioPainelRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vínculo não encontrado"));
        // IDs constantes, não atualiza campos
        return toDTO(entity);
    }

    public List<PainelDTO> listarPaineisPorUsuario(Long usuarioId) {
        List<Painel> paineis = usuarioPainelRepository.findPainelByUsuarioId(usuarioId);
        return paineis.stream()
                .map(p -> {
                    PainelDTO dto = new PainelDTO();
                    dto.setId(p.getId());
                    dto.setDescricao(p.getDescricao());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    private UsuarioPainelDTO toDTO(UsuarioPainel entity) {
        UsuarioPainelDTO dto = new UsuarioPainelDTO();
        dto.setId(entity.getId());
        dto.setPainelId(entity.getPainelId());
        dto.setUsuarioCadastradoId(entity.getUsuarioCadastradoId());
        dto.setUsuarioId(entity.getUsuarioId());
        dto.setDataRegistro(entity.getDataRegistro());
        return dto;
    }
}