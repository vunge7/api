package com.dvml.api.controller;

import com.dvml.api.dto.PainelPermissaoDTO;
import com.dvml.api.service.PainelPermissaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/painelpermissoes")
public class PainelPermissaoController {

    @Autowired
    private PainelPermissaoService painelPermissaoService;

    @PostMapping("/add")
    public ResponseEntity<PainelPermissaoDTO> add(
            @RequestBody PainelPermissaoDTO dto,
            @RequestHeader(value = "Usuario-Id", required = false) Long usuarioIdCriacao) {
        if (usuarioIdCriacao == null) {
            usuarioIdCriacao = 1L; // Valor padrão
        }
        PainelPermissaoDTO created = painelPermissaoService.create(dto, usuarioIdCriacao);
        return ResponseEntity.ok(created);
    }

    // Outros métodos do controlador (mantidos conforme o original)
    @GetMapping("/{id}")
    public ResponseEntity<PainelPermissaoDTO> getById(@PathVariable Long id) {
        PainelPermissaoDTO dto = painelPermissaoService.findById(id);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("all")
    public ResponseEntity<List<PainelPermissaoDTO>> getAll() {
        List<PainelPermissaoDTO> dtos = painelPermissaoService.findAll();
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<PainelPermissaoDTO>> getByUsuarioId(@PathVariable Long usuarioId) {
        List<PainelPermissaoDTO> dtos = painelPermissaoService.findByUsuarioId(usuarioId);
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/usuario/{usuarioId}/filial/{filialId}")
    public ResponseEntity<List<PainelPermissaoDTO>> getByUsuarioIdAndFilialId(
            @PathVariable Long usuarioId,
            @PathVariable Long filialId) {
        List<PainelPermissaoDTO> dtos = painelPermissaoService.findByUsuarioIdAndFilialId(usuarioId, filialId);
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/usuario/{usuarioId}/filiais")
    public ResponseEntity<List<Long>> getFiliaisByUsuarioId(@PathVariable Long usuarioId) {
        List<Long> filiais = painelPermissaoService.findFiliaisByUsuarioId(usuarioId);
        return ResponseEntity.ok(filiais);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PainelPermissaoDTO> update(
            @PathVariable Long id,
            @RequestBody PainelPermissaoDTO dto,
            @RequestHeader("Usuario-Id") Long usuarioIdActualizacao) {
        PainelPermissaoDTO updated = painelPermissaoService.update(id, dto, usuarioIdActualizacao);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        painelPermissaoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}