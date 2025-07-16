package com.dvml.api.controller;

import com.dvml.api.entity.LinhaResultado;
import com.dvml.api.service.LinhaResultadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class LinhaResultadoController {

    @Autowired
    private LinhaResultadoService service;

    @PostMapping("linharesultado/add")
    public ResponseEntity<LinhaResultado> criar(@RequestBody LinhaResultado linhaResultado) {
        return ResponseEntity.ok(service.salvar(linhaResultado));
    }

    @GetMapping("linharesultado/all")
    public ResponseEntity<List<LinhaResultado>> listarTodos() {
        return ResponseEntity.ok(service.listarTodos());
    }

    @GetMapping("linharesultado/resultado/{resultadoId}")
    public ResponseEntity<List<LinhaResultado>> listarPorResultadoId(@PathVariable Long resultadoId) {
        List<LinhaResultado> linhas = service.listarTodos().stream()
                .filter(lr -> lr.getResultadoId() != null && lr.getResultadoId().equals(resultadoId))
                .toList();
        return ResponseEntity.ok(linhas);
    }

    @GetMapping("linharesultado/{id}")
    public ResponseEntity<LinhaResultado> buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("linharesultado/{id}")
    public ResponseEntity<LinhaResultado> atualizar(@PathVariable Long id, @RequestBody LinhaResultado linhaResultado) {
        try {
            LinhaResultado atualizado = service.atualizar(id, linhaResultado);
            return ResponseEntity.ok(atualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("linharesultado/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}