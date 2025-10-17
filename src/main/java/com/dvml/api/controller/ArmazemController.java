package com.dvml.api.controller;

import com.dvml.api.dto.ArmazemDTO;
import com.dvml.api.service.ArmazemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/armazem")
public class ArmazemController {

    private static final Logger logger = LoggerFactory.getLogger(ArmazemController.class);

    @Autowired
    private ArmazemService armazemService;

    // Listar todos os armazéns
    @GetMapping("/all")
    public ResponseEntity<List<ArmazemDTO>> listarTodosArmazens() {
        logger.info("Requisição para listar todos os armazéns");
        List<ArmazemDTO> armazens = armazemService.listarTodasArmazem();
        return ResponseEntity.ok(armazens);
    }

    // Buscar armazém por ID
    @GetMapping("/{id}")
    public ResponseEntity<ArmazemDTO> getArmazemById(@PathVariable Long id) {
        try {
            ArmazemDTO armazem = armazemService.getArmazemById(id);
            return ResponseEntity.ok(armazem);
        } catch (IllegalArgumentException e) {
            logger.error("Erro ao buscar armazém: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // Criar novo armazém
    @PostMapping("/add")
    public ResponseEntity<ArmazemDTO> criarArmazem(@RequestBody ArmazemDTO armazemDTO) {
        try {
            ArmazemDTO novoArmazem = armazemService.criar(armazemDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(novoArmazem);
        } catch (IllegalArgumentException e) {
            logger.error("Erro ao criar armazém: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    // Atualizar armazém
    @PutMapping("/{id}")
    public ResponseEntity<ArmazemDTO> atualizarArmazem(@PathVariable Long id, @RequestBody ArmazemDTO armazemDTO) {
        try {
            armazemDTO.setId(id);
            ArmazemDTO atualizado = armazemService.update(armazemDTO);
            return ResponseEntity.ok(atualizado);
        } catch (IllegalArgumentException e) {
            logger.error("Erro ao atualizar armazém: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    // Deletar armazém
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarArmazem(@PathVariable Long id) {
        try {
            armazemService.deleteArmazem(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            logger.error("Erro ao deletar armazém: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}