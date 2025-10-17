package com.dvml.api.controller;

import com.dvml.api.entity.ResultadoExame;
import com.dvml.api.service.ResultadoExameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/resultado")
public class ResultadoExameController {

    @Autowired
    private ResultadoExameService service;

    // 🔹 Criar um novo resultado de exame
    @PostMapping("/add")
    public ResponseEntity<ResultadoExame> criar(@RequestBody ResultadoExame resultadoExame) {
        ResultadoExame salvo = service.salvar(resultadoExame);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }

    // 🔹 Listar todos os resultados de exames
    @GetMapping("/all")
    public ResponseEntity<List<ResultadoExame>> listarTodos() {
        return ResponseEntity.ok(service.listarTodos());
    }

    // 🔹 Buscar resultado de exame por ID
    @GetMapping("/{id}")
    public ResponseEntity<ResultadoExame> buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // 🔹 Atualizar resultado de exame por ID
    @PutMapping("/{id}")
    public ResponseEntity<String> atualizar(@PathVariable Long id, @RequestBody ResultadoExame resultadoExame) {
        return service.atualizar(id, resultadoExame);
    }

    // 🔹 Deletar resultado de exame por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletar(@PathVariable Long id) {
        return service.deletar(id);
    }
}