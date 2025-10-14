package com.dvml.api.controller;

import com.dvml.api.dto.EmpresaDTO;
import com.dvml.api.service.EmpresaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/empresa")
public class EmpresaController {

    @Autowired
    private EmpresaService service;

    // ===================================
    // ➕ CRIAR EMPRESA
    // ===================================
    @PostMapping("/add")
    public ResponseEntity<?> criar(@RequestBody EmpresaDTO dto) {
        try {
            EmpresaDTO nova = service.criar(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(nova);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("{ \"" + e.getMessage() + "\"}");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{ \"Erro ao criar empresa.\"}");
        }
    }

    // ===================================
    // 🔁 ATUALIZAR EMPRESA
    // ===================================
    @PutMapping("/edit")
    public ResponseEntity<?> atualizar(@RequestBody EmpresaDTO dto) {
        try {
            EmpresaDTO atualizada = service.update(dto);
            return ResponseEntity.ok(atualizada);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("{ \"" + e.getMessage() + "\"}");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"Erro ao atualizar empresa.\"}");
        }
    }

    // ===================================
    // 📋 LISTAR TODAS AS EMPRESAS
    // ===================================
    @GetMapping("/all")
    public ResponseEntity<List<EmpresaDTO>> listarTodas() {
        List<EmpresaDTO> lista = service.listarTodas();
        return ResponseEntity.ok(lista);
    }

    // ===================================
    // 🔍 BUSCAR POR ID
    // ===================================
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        EmpresaDTO dto = service.buscarPorId(id);
        if (dto != null) {
            return ResponseEntity.ok(dto);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("{\"Empresa não encontrada.\"}");
        }
    }

    // ===================================
    // ❌ DELETAR EMPRESA
    // ===================================
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id) {
        boolean deleted = service.deletarPorId(id);
        if (deleted) {
            return ResponseEntity.ok("{ \"Empresa deletada com sucesso.\"}");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("{\"Empresa não encontrada.\"}");
        }
    }
}
