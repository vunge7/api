package com.dvml.api.controller;

import com.dvml.api.dto.DepartamentoDTO;
import com.dvml.api.service.DepartamentoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/departamento")
public class DepartamentoController {

    private static final Logger logger = LoggerFactory.getLogger(DepartamentoController.class);

    @Autowired
    private DepartamentoService departamentoService;

    // Listar todos os departamentos
    @GetMapping("/all")
    public ResponseEntity<List<DepartamentoDTO>> listarTodosDepartamentos() {
        logger.info("Requisição para listar todos os departamentos");
        List<DepartamentoDTO> departamentos = departamentoService.findAll();
        return ResponseEntity.ok(departamentos);
    }

    // Buscar departamento por ID
    @GetMapping("/{id}")
    public ResponseEntity<DepartamentoDTO> getDepartamentoById(@PathVariable Long id) {
        try {
            DepartamentoDTO departamento = departamentoService.findById(id);
            return ResponseEntity.ok(departamento);
        } catch (Exception e) {
            logger.error("Erro ao buscar departamento: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // Criar novo departamento
    @PostMapping("/add")
    public ResponseEntity<DepartamentoDTO> criarDepartamento(@RequestBody DepartamentoDTO departamentoDTO) {
        try {
            DepartamentoDTO novoDepartamento = departamentoService.create(departamentoDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(novoDepartamento);
        } catch (IllegalArgumentException e) {
            logger.error("Erro ao criar departamento: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    // Atualizar departamento
    @PutMapping("/{id}")
    public ResponseEntity<DepartamentoDTO> atualizarDepartamento(@PathVariable Long id, @RequestBody DepartamentoDTO departamentoDTO) {
        try {
            DepartamentoDTO atualizado = departamentoService.update(id, departamentoDTO);
            return ResponseEntity.ok(atualizado);
        } catch (IllegalArgumentException | jakarta.persistence.EntityNotFoundException e) {
            logger.error("Erro ao atualizar departamento: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    // Deletar departamento
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarDepartamento(@PathVariable Long id) {
        try {
            departamentoService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (jakarta.persistence.EntityNotFoundException e) {
            logger.error("Erro ao deletar departamento: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}