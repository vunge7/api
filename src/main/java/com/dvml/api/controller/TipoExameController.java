package com.dvml.api.controller;

import com.dvml.api.dto.TipoExameDTO;
import com.dvml.api.service.TipoExameService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/tipo-exame")
public class TipoExameController {
    private static final Logger logger = LoggerFactory.getLogger(TipoExameController.class);

    @Autowired
    private TipoExameService service;

    @GetMapping("/all")
    public ResponseEntity<List<TipoExameDTO>> getAll() {
        logger.info("Requisição para listar todos os tipos de exame");
        List<TipoExameDTO> tipos = service.listarTodos();
        logger.debug("Total de tipos de exame retornados: {}", tipos.size());
        return ResponseEntity.ok(tipos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TipoExameDTO> getById(@PathVariable Long id) {
        logger.info("Requisição para buscar tipo de exame com ID: {}", id);
        TipoExameDTO tipoExame = service.getById(id);
        return ResponseEntity.ok(tipoExame);
    }

    @PostMapping("/add")
    public ResponseEntity<TipoExameDTO> create(@Valid @RequestBody TipoExameDTO dto) {
        logger.info("Requisição para criar tipo de exame: {}", dto.getNome());
        TipoExameDTO created = service.criar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/edit")
    public ResponseEntity<TipoExameDTO> update(@Valid @RequestBody TipoExameDTO dto) {
        logger.info("Requisição para atualizar tipo de exame com ID: {}", dto.getId());
        TipoExameDTO updated = service.atualizar(dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        logger.info("Requisição para deletar tipo de exame com ID: {}", id);
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}