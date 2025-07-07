package com.dvml.api.controller;

import com.dvml.api.dto.ExameDTO;
import com.dvml.api.service.ExameService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/exame")
public class ExameController {
    private static final Logger logger = LoggerFactory.getLogger(ExameController.class);

    @Autowired
    private ExameService service;

    @GetMapping("/all")
    public ResponseEntity<List<ExameDTO>> getAll() {
        logger.info("Requisição para listar todos os exames");
        List<ExameDTO> exames = service.listarTodos();
        logger.debug("Total de exames retornados: {}", exames.size());
        return ResponseEntity.ok(exames);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExameDTO> getById(@PathVariable Long id) {
        logger.info("Requisição para buscar exame com ID: {}", id);
        ExameDTO exame = service.getById(id);
        return ResponseEntity.ok(exame);
    }

    @GetMapping("/paciente/{pacienteId}")
    public ResponseEntity<List<ExameDTO>> getByPacienteId(@PathVariable Long pacienteId) {
        logger.info("Requisição para buscar exames do paciente com ID: {}", pacienteId);
        List<ExameDTO> exames = service.getByPacienteId(pacienteId);
        logger.debug("Total de exames encontrados para o paciente: {}", exames.size());
        return ResponseEntity.ok(exames);
    }

    @PostMapping("/add")
    public ResponseEntity<ExameDTO> create(@Valid @RequestBody ExameDTO dto) {
        logger.info("Requisição para criar exame para requisição ID: {}", dto.getRequisicaoExameId());
        ExameDTO created = service.criar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/edit")
    public ResponseEntity<ExameDTO> update(@Valid @RequestBody ExameDTO dto) {
        logger.info("Requisição para atualizar exame com ID: {}", dto.getId());
        ExameDTO updated = service.atualizar(dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        logger.info("Requisição para deletar exame com ID: {}", id);
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}