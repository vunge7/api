package com.dvml.api.controller;

import com.dvml.api.dto.DepartamentoDTO;
import com.dvml.api.service.DepartamentoService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class DepartamentoController {

    private static final Logger LOGGER = LoggerFactory.getLogger(DepartamentoController.class);

    @Autowired
    private DepartamentoService departamentoService;

    @PostMapping ("departamento/add")
    public ResponseEntity<DepartamentoDTO> create(@Valid @RequestBody DepartamentoDTO departamentoDTO) {
        LOGGER.info("Recebendo requisição para criar departamento: {}", departamentoDTO.getDescricao());
        DepartamentoDTO created = departamentoService.create(departamentoDTO);
        LOGGER.info("Departamento criado com sucesso: ID {}", created.getId());
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PutMapping("departamento/{id}")
    public ResponseEntity<DepartamentoDTO> update(@PathVariable Long id, @Valid @RequestBody DepartamentoDTO departamentoDTO) {
        LOGGER.info("Recebendo requisição para atualizar departamento ID: {}", id);
        DepartamentoDTO updated = departamentoService.update(id, departamentoDTO);
        LOGGER.info("Departamento atualizado com sucesso: ID {}", updated.getId());
        return ResponseEntity.ok(updated);
    }

    @GetMapping("departamento/{id}")
    public ResponseEntity<DepartamentoDTO> getById(@PathVariable Long id) {
        LOGGER.info("Recebendo requisição para buscar departamento ID: {}", id);
        DepartamentoDTO departamento = departamentoService.findById(id);
        LOGGER.info("Departamento encontrado: ID {}", id);
        return ResponseEntity.ok(departamento);
    }

    @GetMapping("departamento/all")
    public ResponseEntity<List<DepartamentoDTO>> getAll() {
        LOGGER.info("Recebendo requisição para listar todos os departamentos");
        List<DepartamentoDTO> departamentos = departamentoService.findAll();
        LOGGER.info("Retornando {} departamentos", departamentos.size());
        return ResponseEntity.ok(departamentos);
    }

    @DeleteMapping("departamento/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        LOGGER.info("Recebendo requisição para deletar departamento ID: {}", id);
        departamentoService.delete(id);
        LOGGER.info("Departamento deletado: ID {}", id);
        return ResponseEntity.noContent().build();
    }
}