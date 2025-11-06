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

    // ✅ Criar uma nova permissão
    @PostMapping("/add")
    public ResponseEntity<PainelPermissaoDTO> create(
            @RequestBody PainelPermissaoDTO dto,
            @RequestParam Long usuarioIdCriacao
    ) {
        PainelPermissaoDTO created = painelPermissaoService.create(dto, usuarioIdCriacao);
        return ResponseEntity.ok(created);
    }

    // ✅ Buscar uma permissão por ID
    @GetMapping("/{id}")
    public ResponseEntity<PainelPermissaoDTO> findById(@PathVariable Long id) {
        PainelPermissaoDTO dto = painelPermissaoService.findById(id);
        return ResponseEntity.ok(dto);
    }

    // ✅ Buscar todas as permissões
    @GetMapping("/all")
    public ResponseEntity<List<PainelPermissaoDTO>> findAll() {
        List<PainelPermissaoDTO> list = painelPermissaoService.findAll();
        return ResponseEntity.ok(list);
    }

    // ✅ Buscar permissões por usuário
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<PainelPermissaoDTO>> findByUsuarioId(@PathVariable Long usuarioId) {
        List<PainelPermissaoDTO> list = painelPermissaoService.findByUsuarioId(usuarioId);
        return ResponseEntity.ok(list);
    }

    // ✅ Buscar permissões por usuário e empresa
    @GetMapping("/usuario/{usuarioId}/empresa/{empresaId}")
    public ResponseEntity<List<PainelPermissaoDTO>> findByUsuarioIdAndEmpresaId(
            @PathVariable Long usuarioId,
            @PathVariable Long empresaId
    ) {
        List<PainelPermissaoDTO> list = painelPermissaoService.findByUsuarioIdAndEmpresaId(usuarioId, empresaId);
        return ResponseEntity.ok(list);
    }

    // ✅ Buscar lista de empresas com permissão de um usuário
    @GetMapping("/usuario/{usuarioId}/empresas")
    public ResponseEntity<List<Long>> findEmpresasByUsuarioId(@PathVariable Long usuarioId) {
        List<Long> empresas = painelPermissaoService.findEmpresasByUsuarioId(usuarioId);
        return ResponseEntity.ok(empresas);
    }

    // ✅ Atualizar uma permissão existente
    @PutMapping("/{id}")
    public ResponseEntity<PainelPermissaoDTO> update(
            @PathVariable Long id,
            @RequestBody PainelPermissaoDTO dto,
            @RequestParam Long usuarioIdActualizacao
    ) {
        PainelPermissaoDTO updated = painelPermissaoService.update(id, dto, usuarioIdActualizacao);
        return ResponseEntity.ok(updated);
    }

    // ✅ Deletar uma permissão
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        painelPermissaoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
