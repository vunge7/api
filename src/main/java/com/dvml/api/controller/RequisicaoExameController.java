package com.dvml.api.controller;

import com.dvml.api.dto.RequisicaoExameDTO;
import com.dvml.api.entity.RequisicaoExame;
import com.dvml.api.service.RequisicaoExameService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/requisicaoexame")
public class RequisicaoExameController {

    private static final Logger logger = LoggerFactory.getLogger(RequisicaoExameController.class);

    @Autowired
    private RequisicaoExameService service;

    @GetMapping("/all")
    public ResponseEntity<List<RequisicaoExame>> getAllRequisicoes() {
        logger.info("Buscando todas as requisições de exame");
        try {
            List<RequisicaoExame> requisicoes = service.listarTodasRequisicoes();
            logger.info("Encontradas {} requisições", requisicoes.size());
            return ResponseEntity.ok(requisicoes);
        } catch (Exception e) {
            logger.error("Erro ao buscar requisições: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/all/composto")
    public ResponseEntity<List<RequisicaoExameDTO>> getAllRequisicoesComposto() {
        logger.info("Buscando todas as requisições compostas");
        try {
            List<RequisicaoExameDTO> requisicoes = service.listarTodasRequisicoesComposto();
            logger.info("Encontradas {} requisições compostas", requisicoes.size());
            return ResponseEntity.ok(requisicoes);
        } catch (Exception e) {
            logger.error("Erro ao buscar requisições compostas: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<RequisicaoExame> getRequisicaoById(@PathVariable long id) {
        logger.info("Buscando requisição com ID: {}", id);
        RequisicaoExame requisicao = service.getRequisicaoById(id);
        if (requisicao == null) {
            logger.warn("Requisição com ID {} não encontrada", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(requisicao);
    }

    @PostMapping("/add")
    public ResponseEntity<RequisicaoExame> adicionar(@RequestBody @Valid RequisicaoExame requisicaoExame) {
        logger.info("Criando nova requisição: {}", requisicaoExame);
        try {
            RequisicaoExame novaRequisicao = service.criar(requisicaoExame);
            logger.info("Requisição criada com ID: {}", novaRequisicao.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(novaRequisicao);
        } catch (Exception e) {
            logger.error("Erro ao criar requisição: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PutMapping("/edit")
    public ResponseEntity<String> updateRequisicao(@RequestBody @Valid RequisicaoExame requisicaoExame) {
        Long id = requisicaoExame.getId();
        logger.info("Atualizando requisição com ID: {}, payload: {}", id, requisicaoExame);

        try {
            if (id == null) {
                logger.warn("ID da requisição é nulo");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("ID da requisição é obrigatório");
            }

            if (service.getRequisicaoById(id) == null) {
                logger.warn("Requisição com ID {} não encontrada", id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Requisição não encontrada");
            }

            service.update(requisicaoExame);
            logger.info("Requisição com ID {} atualizada com sucesso", id);
            return ResponseEntity.ok("Requisição atualizada com sucesso");

        } catch (Exception e) {
            logger.error("Erro ao atualizar requisição com ID {}: {}", id, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao atualizar requisição: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRequisicao(@PathVariable long id) {
        logger.info("Excluindo requisição com ID: {}", id);
        try {
            if (service.getRequisicaoById(id) == null) {
                logger.warn("Requisição com ID {} não encontrada", id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Requisição não encontrada");
            }

            service.deleteRequisicao(id);
            logger.info("Requisição com ID {} excluída com sucesso", id);
            return ResponseEntity.ok("Requisição excluída com sucesso");

        } catch (Exception e) {
            logger.error("Erro ao excluir requisição com ID {}: {}", id, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao excluir requisição: " + e.getMessage());
        }
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        logger.warn("Erro de validação: {}", ex.getMessage());
        Map<String, String> errors = new HashMap<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
            logger.warn("Campo inválido: {} - {}", error.getField(), error.getDefaultMessage());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }
}
