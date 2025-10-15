package com.dvml.api.controller;

import com.dvml.api.dto.ExameDTO;
import com.dvml.api.service.ExameService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/exame")
public class ExameController {

    private static final Logger logger = LoggerFactory.getLogger(ExameController.class);

    @Autowired
    private ExameService exameService;

    // Listar todos os exames
    @GetMapping("/all")
    public ResponseEntity<List<ExameDTO>> listarTodosExames() {
        logger.info("Requisição para listar todos os exames");
        List<ExameDTO> exames = exameService.listarTodos();
        return ResponseEntity.ok(exames);
    }

    // Buscar exame por ID
    @GetMapping("/{id}")
    public ResponseEntity<ExameDTO> getExameById(@PathVariable Long id) {
        try {
            ExameDTO exame = exameService.getById(id);
            return ResponseEntity.ok(exame);
        } catch (IllegalArgumentException e) {
            logger.error("Erro ao buscar exame: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // Buscar exames por paciente
    @GetMapping("/paciente/{pacienteId}")
    public ResponseEntity<List<ExameDTO>> getExamesByPacienteId(@PathVariable Long pacienteId) {
        try {
            List<ExameDTO> exames = exameService.getByPacienteId(pacienteId);
            return ResponseEntity.ok(exames);
        } catch (IllegalArgumentException e) {
            logger.error("Erro ao buscar exames por paciente: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // Criar novo exame
    @PostMapping("/add")
    public ResponseEntity<ExameDTO> criarExame(@RequestBody ExameDTO dto) {
        try {
            ExameDTO novoExame = exameService.criar(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(novoExame);
        } catch (IllegalArgumentException e) {
            logger.error("Erro ao criar exame: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    // Atualizar exame
    @PutMapping("/{id}")
    public ResponseEntity<ExameDTO> atualizarExame(@PathVariable Long id, @RequestBody ExameDTO dto) {
        try {
            dto.setId(id);
            ExameDTO atualizado = exameService.atualizar(dto);
            return ResponseEntity.ok(atualizado);
        } catch (IllegalArgumentException e) {
            logger.error("Erro ao atualizar exame: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    // Deletar exame
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarExame(@PathVariable Long id) {
        try {
            exameService.deletar(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            logger.error("Erro ao deletar exame: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}