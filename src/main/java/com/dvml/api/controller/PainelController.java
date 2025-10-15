package com.dvml.api.controller;

import com.dvml.api.dto.PainelDTO;
import com.dvml.api.service.PainelService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping ("/painel")
public class PainelController {

    @Autowired
    private PainelService service;

    // 🔹 Criar um novo painel
    @PostMapping("/add")
    public ResponseEntity<PainelDTO> create(@Valid @RequestBody PainelDTO dto) {
        return ResponseEntity.ok(service.create(dto));
    }

    // 🔹 Listar todos os painéis
    @GetMapping("/all")
    public ResponseEntity<List<PainelDTO>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    // 🔹 Buscar um painel pelo ID
    @GetMapping("/{id}")
    public ResponseEntity<PainelDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    // 🔹 Atualizar um painel existente
    @PutMapping("/{id}")
    public ResponseEntity<PainelDTO> update(@PathVariable Long id, @Valid @RequestBody PainelDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    // 🔹 Deletar (remover) um painel pelo ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
