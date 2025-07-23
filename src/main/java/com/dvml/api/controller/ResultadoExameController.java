package com.dvml.api.controller;

import com.dvml.api.entity.ResultadoExame;
import com.dvml.api.service.ResultadoExameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ResultadoExameController {

    @Autowired
    private ResultadoExameService service;

    // 🔹 Criar um novo resultado de exame
    @PostMapping("resultado/add")
    public ResponseEntity<ResultadoExame> criar(@RequestBody ResultadoExame resultadoExame) {
        ResultadoExame salvo = service.salvar(resultadoExame);
        return ResponseEntity.ok(salvo);
    }

    // 🔹 Listar todos os resultados de exames
    @GetMapping("resultado/all")
    public ResponseEntity<List<ResultadoExame>> listarTodos() {
        return ResponseEntity.ok(service.listarTodos());
    }

    // 🔹 Buscar resultado de exame por ID
    @GetMapping("resultado/{id}")
    public ResponseEntity<ResultadoExame> buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // 🔹 Atualizar resultado de exame por ID
    @PutMapping("resultado/{id}")
    public ResponseEntity<ResultadoExame> atualizar(@PathVariable Long id, @RequestBody ResultadoExame resultadoExame) {
        try {
            ResultadoExame atualizado = service.atualizar(id, resultadoExame);
            return ResponseEntity.ok(atualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // 🔹 Deletar resultado de exame por ID
    @DeleteMapping("resultado/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
