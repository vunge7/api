package com.dvml.api.controller;

import com.dvml.api.dto.PainelPermissaoDTO;
import com.dvml.api.service.PainelPermissaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/painelpermissoes")
public class PainelPermissaoController {

    @Autowired
    private PainelPermissaoService painelPermissaoService;

    // 🔹 Adicionar nova permissão a um painel para um usuário e filial
    @PostMapping("/add")
    public ResponseEntity<?> add(
            @RequestBody PainelPermissaoDTO dto,
            @RequestHeader(value = "Usuario-Id", required = false) Long usuarioIdCriacao) {

        if (usuarioIdCriacao == null) {
            usuarioIdCriacao = 1L; // Valor padrão
        }

        try {
            PainelPermissaoDTO created = painelPermissaoService.create(dto, usuarioIdCriacao);
            return ResponseEntity.ok(created);
        } catch (RuntimeException ex) {
            // 🔸 Retorna erro 409 se a permissão já existir
            return ResponseEntity
                    .status(409)
                    .body(Map.of("mensagem", ex.getMessage()));
        }
    }

    // 🔹 Buscar permissão por ID
    @GetMapping("/{id}")
    public ResponseEntity<PainelPermissaoDTO> getById(@PathVariable Long id) {
        PainelPermissaoDTO dto = painelPermissaoService.findById(id);
        return ResponseEntity.ok(dto);
    }

    // 🔹 Listar todas as permissões existentes
    @GetMapping("all")
    public ResponseEntity<List<PainelPermissaoDTO>> getAll() {
        List<PainelPermissaoDTO> dtos = painelPermissaoService.findAll();
        return ResponseEntity.ok(dtos);
    }

    // 🔹 Listar permissões de um usuário específico
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<PainelPermissaoDTO>> getByUsuarioId(@PathVariable Long usuarioId) {
        List<PainelPermissaoDTO> dtos = painelPermissaoService.findByUsuarioId(usuarioId);
        return ResponseEntity.ok(dtos);
    }

    // 🔹 Listar permissões de um usuário por filial específica
    @GetMapping("/usuario/{usuarioId}/filial/{filialId}")
    public ResponseEntity<List<PainelPermissaoDTO>> getByUsuarioIdAndFilialId(
            @PathVariable Long usuarioId,
            @PathVariable Long filialId) {
        List<PainelPermissaoDTO> dtos = painelPermissaoService.findByUsuarioIdAndFilialId(usuarioId, filialId);
        return ResponseEntity.ok(dtos);
    }

    // 🔹 Listar filiais às quais o usuário tem permissão
    @GetMapping("/usuario/{usuarioId}/filiais")
    public ResponseEntity<List<Long>> getFiliaisByUsuarioId(@PathVariable Long usuarioId) {
        List<Long> filiais = painelPermissaoService.findFiliaisByUsuarioId(usuarioId);
        return ResponseEntity.ok(filiais);
    }

    // 🔹 Atualizar uma permissão existente
    @PutMapping("/{id}")
    public ResponseEntity<PainelPermissaoDTO> update(
            @PathVariable Long id,
            @RequestBody PainelPermissaoDTO dto,
            @RequestHeader("Usuario-Id") Long usuarioIdActualizacao) {
        PainelPermissaoDTO updated = painelPermissaoService.update(id, dto, usuarioIdActualizacao);
        return ResponseEntity.ok(updated);
    }

    // 🔹 Deletar uma permissão por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        painelPermissaoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
