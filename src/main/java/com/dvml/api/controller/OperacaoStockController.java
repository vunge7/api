package com.dvml.api.controller;

import com.dvml.api.dto.OperacaoStockDTO;
import com.dvml.api.entity.OperacaoStock;
import com.dvml.api.service.OperacaoStockService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/operacao-stock")
public class OperacaoStockController {

    private static final Logger logger = LoggerFactory.getLogger(OperacaoStockController.class);

    @Autowired
    private OperacaoStockService service;

    @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<OperacaoStock>> getAllOperacaoStock() {
        logger.info("Recebida requisição para listar todas as operações de estoque");
        try {
            List<OperacaoStock> operacoes = service.listarTodasOperacaoStock();
            return ResponseEntity.ok(operacoes);
        } catch (Exception e) {
            logger.error("Erro ao listar operações: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OperacaoStock> getOperacaoStockById(@PathVariable Long id) {
        logger.info("Recebida requisição para buscar operação de estoque com ID: {}", id);
        try {
            OperacaoStock operacao = service.getOperacaoStockById(id);
            return ResponseEntity.ok(operacao);
        } catch (RuntimeException e) {
            logger.error("Erro ao buscar operação com ID {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping(value = "/add", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OperacaoStock> adicionar(@RequestBody @Valid OperacaoStock operacao) {
        logger.info("Recebida requisição para adicionar operação: {}", operacao);
        try {
            OperacaoStock novaOperacao = service.criar(operacao);
            return ResponseEntity.status(HttpStatus.CREATED).body(novaOperacao);
        } catch (IllegalArgumentException e) {
            logger.error("Erro de validação ao adicionar operação: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (Exception e) {
            logger.error("Erro interno ao adicionar operação: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping(value = "/add-with-linhas", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OperacaoStock> adicionarComLinhas(@RequestBody @Valid OperacaoStockDTO operacaoDTO) {
        logger.info("Recebida requisição para adicionar operação com linhas: {}", operacaoDTO);
        try {
            OperacaoStock novaOperacao = service.criarComLinhas(operacaoDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(novaOperacao);
        } catch (IllegalArgumentException e) {
            logger.error("Erro de validação ao adicionar operação com linhas: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (Exception e) {
            logger.error("Erro interno ao adicionar operação com linhas: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PutMapping(value = "/edit/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OperacaoStock> updateOperacaoStock(@PathVariable Long id, @RequestBody @Valid OperacaoStock operacao) {
        logger.info("Recebida requisição para atualizar operação com ID: {}", id);
        try {
            operacao.setId(id);
            OperacaoStock atualizada = service.update(operacao);
            return ResponseEntity.ok(atualizada);
        } catch (RuntimeException e) {
            logger.error("Erro ao atualizar operação com ID {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            logger.error("Erro interno ao atualizar operação: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> deleteOperacaoStock(@PathVariable Long id) {
        logger.info("Recebida requisição para excluir operação com ID: {}", id);
        try {
            service.deleteOperacaoStock(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            logger.error("Erro ao excluir operação com ID {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            logger.error("Erro interno ao excluir operação: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex) {
        logger.error("Erro de validação: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGeneralException(Exception ex) {
        logger.error("Erro interno no servidor: {}", ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro interno no servidor: " + ex.getMessage());
    }
}