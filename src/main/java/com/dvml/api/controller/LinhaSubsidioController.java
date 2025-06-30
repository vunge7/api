package com.dvml.api.controller;

import com.dvml.api.dto.LinhaSubsidioDTO;
import com.dvml.api.service.LinhaSubsidioService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class LinhaSubsidioController {

    private static final Logger LOGGER = LoggerFactory.getLogger(LinhaSubsidioController.class);

    @Autowired
    private LinhaSubsidioService linhaSubsidioService;

    @PostMapping("linhasubsidio/add")
    public ResponseEntity<Void> create(@Valid @RequestBody List<LinhaSubsidioDTO> dtos) {
        LOGGER.info("Criando {} linhas de subsídio", dtos.size());
        if (!dtos.isEmpty()) {
            Long funcionarioId = dtos.get(0).getFuncionarioId();
            linhaSubsidioService.createFromRequest(funcionarioId, dtos);
        }
        LOGGER.info("Linhas de subsídio criadas com sucesso");
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("linhasubsidio/all")
    public ResponseEntity<List<LinhaSubsidioDTO>> findAll() {
        LOGGER.info("Buscando todas as linhas de subsídio");
        List<LinhaSubsidioDTO> subsidios = linhaSubsidioService.findAll();
        return ResponseEntity.ok(subsidios);
    }

    @GetMapping("linhasubsidio/{id}")
    public ResponseEntity<LinhaSubsidioDTO> findById(@PathVariable("id") Long id) {
        LOGGER.info("Buscando subsídio ID: {}", id);
        LinhaSubsidioDTO dto = linhaSubsidioService.findById(id);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("linhasubsidio/funcionario/{funcionarioId}")
    public ResponseEntity<List<LinhaSubsidioDTO>> findByFuncionarioId(@PathVariable("funcionarioId") Long funcionarioId) {
        LOGGER.info("Buscando subsídios para funcionário ID: {}", funcionarioId);
        List<LinhaSubsidioDTO> subsidios = linhaSubsidioService.findByFuncionarioId(funcionarioId);
        return ResponseEntity.ok(subsidios);
    }

    @PutMapping("linhasubsidio/{id}")
    public ResponseEntity<LinhaSubsidioDTO> update(@PathVariable("id") Long id, @Valid @RequestBody LinhaSubsidioDTO dto) {
        LOGGER.info("Atualizando subsídio ID: {}", id);
        dto.setId(id);
        LinhaSubsidioDTO updated = linhaSubsidioService.update(dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("linhasubsidio/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Long id) {
        LOGGER.info("Deletando subsídio ID: {}", id);
        try {
            linhaSubsidioService.delete(id);
            return ResponseEntity.status(HttpStatus.OK).body("Subsídio deletado com sucesso!");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Subsídio não encontrado com ID: " + id);
        }
    }
}