package com.dvml.api.controller;

import com.dvml.api.dto.ArmazemDTO;
import com.dvml.api.service.ArmazemService;
import jakarta.validation.Valid;
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

    // LISTAR TODOS
    @GetMapping("/all")
    public ResponseEntity<List<ArmazemDTO>> listarTodosArmazens() {
        logger.info("GET /armazem/all – Listando todos os armazéns");
        List<ArmazemDTO> armazens = armazemService.listarTodasArmazem();
        return ResponseEntity.ok(armazens);
    }

    // BUSCAR POR ID
    @GetMapping("/{id}")
    public ResponseEntity<ArmazemDTO> getArmazemById(@PathVariable Long id) {
        logger.info("GET /armazem/{} – Buscando armazém", id);
        try {
            ArmazemDTO armazem = armazemService.getArmazemById(id);
            return ResponseEntity.ok(armazem);
        } catch (IllegalArgumentException e) {
            logger.warn("Armazém não encontrado: ID {}", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // CRIAR
    @PostMapping("/add")
    public ResponseEntity<ArmazemDTO> criarArmazem(@Valid @RequestBody ArmazemDTO armazemDTO) {
        logger.info("POST /armazem/add – Criando armazém: {}", armazemDTO.getDesignacao());
        try {
            ArmazemDTO novo = armazemService.criar(armazemDTO);
            logger.info("Armazém criado com sucesso: ID {}", novo.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(novo);
        } catch (IllegalArgumentException e) {
            logger.warn("Falha ao criar: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    // ATUALIZAR – RETORNA OBJETO ATUALIZADO
    @PutMapping("/{id}")
    public ResponseEntity<ArmazemDTO> atualizarArmazem(
            @PathVariable Long id,
            @Valid @RequestBody ArmazemDTO armazemDTO) {

        logger.info("PUT /armazem/{} – Atualizando armazém", id);

        armazemDTO.setId(id); // Garante que o ID é o mesmo

        try {
            ArmazemDTO atualizado = armazemService.update(armazemDTO);
            logger.info("Armazém atualizado com sucesso: ID {}", id);
            return ResponseEntity.ok(atualizado); // OBJETO COMPLETO RETORNADO
        } catch (IllegalArgumentException e) {
            logger.warn("Erro ao atualizar ID {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (Exception e) {
            logger.error("Erro interno ao atualizar ID {}: {}", id, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // DELETAR
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarArmazem(@PathVariable Long id) {
        logger.info("DELETE /armazem/{} – Deletando armazém", id);
        try {
            armazemService.deleteArmazem(id);
            logger.info("Armazém deletado: ID {}", id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            logger.warn("Armazém não encontrado para exclusão: ID {}", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}