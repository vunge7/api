package com.dvml.api.controller;

import com.dvml.api.dto.PainelPermissaoDTO;
import com.dvml.api.service.PainelPermissaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/painelpermissoes")
public class PainelPermissaoController {

    @Autowired
    private PainelPermissaoService painelPermissaoService;

    // -------------------- CRIAR --------------------

    @PostMapping("/add")
    public ResponseEntity<PainelPermissaoDTO> create(@RequestBody PainelPermissaoDTO dto) {
        PainelPermissaoDTO created = painelPermissaoService.create(dto, dto.getUsuarioIdCriacao());
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // -------------------- LEITURA --------------------

    // Buscar uma permissão por ID
    @GetMapping("/{id}")
    public ResponseEntity<PainelPermissaoDTO> findById(@PathVariable Long id) {
        PainelPermissaoDTO dto = painelPermissaoService.findById(id);
        return ResponseEntity.ok(dto);
    }

    // Buscar todas as permissões
    @GetMapping("/all")
    public ResponseEntity<List<PainelPermissaoDTO>> findAll() {
        List<PainelPermissaoDTO> list = painelPermissaoService.findAll();
        return ResponseEntity.ok(list);
    }

    // Buscar permissões por usuário
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<PainelPermissaoDTO>> findByUsuarioId(@PathVariable Long usuarioId) {
        List<PainelPermissaoDTO> list = painelPermissaoService.findByUsuarioId(usuarioId);
        return ResponseEntity.ok(list);
    }

    // Buscar permissões por usuário e empresa
    @GetMapping("/usuario/{usuarioId}/empresa/{empresaId}")
    public ResponseEntity<List<PainelPermissaoDTO>> findByUsuarioIdAndEmpresaId(
            @PathVariable Long usuarioId,
            @PathVariable Long empresaId
    ) {
        List<PainelPermissaoDTO> list = painelPermissaoService.findByUsuarioIdAndEmpresaId(usuarioId, empresaId);
        return ResponseEntity.ok(list);
    }

    // Buscar lista de empresas onde o usuário tem permissão
    @GetMapping("/usuario/{usuarioId}/empresas")
    public ResponseEntity<List<Long>> findEmpresasByUsuarioId(@PathVariable Long usuarioId) {
        List<Long> empresas = painelPermissaoService.findEmpresasByUsuarioId(usuarioId);
        return ResponseEntity.ok(empresas);
    }

    // -------------------- ATUALIZAÇÃO --------------------

    @PutMapping("/{id}")
    public ResponseEntity<PainelPermissaoDTO> update(
            @PathVariable Long id,
            @RequestBody PainelPermissaoDTO dto
    ) {
        // pega o usuarioIdActualizacao direto do DTO
        PainelPermissaoDTO updated = painelPermissaoService.update(id, dto, dto.getUsuarioIdActualizacao());
        return ResponseEntity.ok(updated);
    }

    // -------------------- DELEÇÃO --------------------

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        painelPermissaoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
