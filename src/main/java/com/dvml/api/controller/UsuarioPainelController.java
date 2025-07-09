package com.dvml.api.controller;

import com.dvml.api.dto.PainelDTO;
import com.dvml.api.dto.UsuarioPainelDTO;
import com.dvml.api.service.UsuarioPainelService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/upainel")
public class UsuarioPainelController {

    @Autowired
    private UsuarioPainelService service;

    @PostMapping("/add")
    public ResponseEntity<UsuarioPainelDTO> create(@Valid @RequestBody UsuarioPainelDTO dto) {
        return ResponseEntity.ok(service.create(dto));
    }

    @GetMapping("/all")
    public ResponseEntity<List<UsuarioPainelDTO>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioPainelDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioPainelDTO> update(@PathVariable Long id, @Valid @RequestBody UsuarioPainelDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @GetMapping("/usuarios/{usuarioId}/paineis")
    public ResponseEntity<List<PainelDTO>> listarPaineisPorUsuario(@PathVariable Long usuarioId) {
        List<PainelDTO> paineis = service.listarPaineisPorUsuario(usuarioId);
        return ResponseEntity.ok(paineis);
    }
}