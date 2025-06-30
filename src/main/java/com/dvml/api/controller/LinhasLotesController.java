package com.dvml.api.controller;

import com.dvml.api.dto.LinhasLotesDTO;
import com.dvml.api.entity.LinhasLotes;
import com.dvml.api.service.LinhasLotesService;
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
public class LinhasLotesController {

    private static final Logger logger = LoggerFactory.getLogger(LinhasLotesController.class);

    @Autowired
    private LinhasLotesService linhasLotesService;

    @PostMapping("linhaslotes/add")
    public ResponseEntity<?> criarLinhasLotes(@Valid @RequestBody LinhasLotesDTO linhasLotesDTO) {
        logger.info("Recebendo requisição para criar LinhasLotes com lotes_id: {} e produto_id: {}",
                linhasLotesDTO.getLotes_id(), linhasLotesDTO.getProduto_id());
        try {
            LinhasLotes created = linhasLotesService.criarLinhasLotes(linhasLotesDTO);
            return new ResponseEntity<>(created, HttpStatus.CREATED);
        } catch (IllegalArgumentException | EntityNotFoundException e) {
            logger.error("Erro ao criar LinhasLotes: {}", e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("linhaslotes/edit")
    public ResponseEntity<?> atualizarLinhasLotes(@PathVariable Long id, @Valid @RequestBody LinhasLotesDTO linhasLotesDTO) {
        logger.info("Recebendo requisição para atualizar LinhasLotes com ID: {}", id);
        try {
            LinhasLotes updated = linhasLotesService.atualizarLinhasLotes(id, linhasLotesDTO);
            return new ResponseEntity<>(updated, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            logger.error("Erro ao atualizar LinhasLotes: {}", e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("linhaslotes/{id}")
    public ResponseEntity<String> deletarLinhasLotes(@PathVariable Long id) {
        logger.info("Recebendo requisição para excluir LinhasLotes com ID: {}", id);
        try {
            linhasLotesService.deletarLinhasLotes(id);
            return new ResponseEntity<>("LinhasLotes com ID " + id + " deletado com sucesso!", HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            logger.error("Erro ao deletar LinhasLotes: {}", e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("linhaslotes/all")
    public ResponseEntity<List<LinhasLotesDTO>> listarTodos() {
        logger.info("Recebendo requisição para listar todas as LinhasLotes");
        List<LinhasLotesDTO> linhasLotes = linhasLotesService.listarTodos();
        return new ResponseEntity<>(linhasLotes, HttpStatus.OK);
    }

    @GetMapping("linhaslotes/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        logger.info("Recebendo requisição para buscar LinhasLotes com ID: {}", id);
        try {
            LinhasLotesDTO linhasLotesDTO = linhasLotesService.buscarPorId(id);
            return new ResponseEntity<>(linhasLotesDTO, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            logger.error("Erro ao buscar LinhasLotes: {}", e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
