package com.dvml.api.controller;

import com.dvml.api.dto.LotesDTO;
import com.dvml.api.service.LotesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/lotes")
public class LotesController {

    private static final Logger logger = LoggerFactory.getLogger(LotesController.class);

    @Autowired
    private LotesService lotesService;

    @PostMapping("/add")
    public ResponseEntity<?> createLote(@RequestBody @Valid LotesDTO lotesDTO) {
        logger.info("Criando lote: {}", lotesDTO.getDesignacao());
        try {
            LotesDTO savedLote = lotesService.save(lotesDTO);
            return new ResponseEntity<>(savedLote, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            logger.error("Erro de validação: {}", e.getMessage());
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        } catch (Exception e) {
            logger.error("Erro interno: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "Erro interno: " + e.getMessage()));
        }
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllLotes() {
        logger.info("Listando todos os lotes");
        try {
            List<LotesDTO> lotes = lotesService.findAll();
            return ResponseEntity.ok(lotes);
        } catch (Exception e) {
            logger.error("Erro ao listar lotes: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "Erro interno: " + e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getLoteById(@PathVariable Long id) {
        logger.info("Buscando lote ID: {}", id);
        try {
            LotesDTO lote = lotesService.findById(id);
            return ResponseEntity.ok(lote);
        } catch (IllegalArgumentException e) {
            logger.error("Erro de validação: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", e.getMessage()));
        } catch (Exception e) {
            logger.error("Erro interno: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "Erro interno: " + e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateLote(@PathVariable Long id, @RequestBody @Valid LotesDTO lotesDTO) {
        logger.info("Atualizando lote ID: {}", id);
        try {
            lotesDTO.setId(id);
            LotesDTO updatedLote = lotesService.update(id, lotesDTO);
            return ResponseEntity.ok(updatedLote);
        } catch (IllegalArgumentException e) {
            logger.error("Erro de validação: {}", e.getMessage());
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        } catch (Exception e) {
            logger.error("Erro interno: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "Erro interno: " + e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteLote(@PathVariable Long id) {
        logger.info("Deletando lote ID: {}", id);
        try {
            lotesService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            logger.error("Erro de validação: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", e.getMessage()));
        } catch (Exception e) {
            logger.error("Erro interno: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "Erro interno: " + e.getMessage()));
        }
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        logger.error("Erro de validação: {}", ex.getMessage());
        Map<String, String> errors = new HashMap<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }
        return ResponseEntity.badRequest().body(errors);
    }
}