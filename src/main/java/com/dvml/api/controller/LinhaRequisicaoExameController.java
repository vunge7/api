package com.dvml.api.controller;

import com.dvml.api.entity.LinhaRequisicaoExame;
import com.dvml.api.service.LinhaRequisicaoExameService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/linharequisicaoexame")
public class LinhaRequisicaoExameController {
    private static final Logger logger = LoggerFactory.getLogger(LinhaRequisicaoExameController.class);

    @Autowired
    private LinhaRequisicaoExameService service;

    @GetMapping("/all")
    public ResponseEntity<List<LinhaRequisicaoExame>> getAllLinhasRequisicao() {
        logger.info("Buscando todas as linhas de requisição de exame");
        try {
            List<LinhaRequisicaoExame> linhas = service.listarTodas();
            logger.info("Encontradas {} linhas de requisição", linhas.size());
            return ResponseEntity.ok(linhas);
        } catch (Exception e) {
            logger.error("Erro ao buscar todas as linhas de requisição: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<LinhaRequisicaoExame> getLinhaRequisicaoById(@PathVariable long id) {
        logger.info("Buscando linha de requisição com ID: {}", id);
        LinhaRequisicaoExame exame = service.getLinhaRequisicaoById(id);
        if (exame == null) {
            logger.warn("Linha de requisição com ID {} não encontrada", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(exame);
    }

    @PostMapping("/add")
    public ResponseEntity<String> adicionar(@RequestBody @Valid LinhaRequisicaoExame linhaRequisicaoExame) {
        logger.info("Criando nova linha de requisição: {}", linhaRequisicaoExame);
        try {
            service.criar(linhaRequisicaoExame);
            return ResponseEntity.status(HttpStatus.CREATED).body("Exame criado com sucesso");
        } catch (Exception e) {
            logger.error("Erro ao criar linha de requisição: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao criar exame: " + e.getMessage());
        }
    }

    @PutMapping("/edit")
    public ResponseEntity<String> updateLinhaRequisicao(@RequestBody @Valid LinhaRequisicaoExame linhaRequisicaoExame) {
        logger.info("Atualizando linha de requisição com ID: {}, payload: {}", linhaRequisicaoExame.getId(), linhaRequisicaoExame);
        try {
            if (linhaRequisicaoExame.getId() == null) {
                logger.warn("ID da linha de requisição é nulo");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("ID da linha de requisição é obrigatório");
            }
            if (service.getLinhaRequisicaoById(linhaRequisicaoExame.getId()) == null) {
                logger.warn("Linha de requisição com ID {} não encontrada", linhaRequisicaoExame.getId());
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Exame não encontrado");
            }
            service.update(linhaRequisicaoExame);
            logger.info("Linha de requisição com ID {} atualizada com sucesso", linhaRequisicaoExame.getId());
            return ResponseEntity.ok("Exame atualizado com sucesso");
        } catch (Exception e) {
            logger.error("Erro ao atualizar linha de requisição com ID {}: {}", linhaRequisicaoExame.getId(), e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao atualizar exame: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteLinhaRequisicao(@PathVariable long id) {
        logger.info("Excluindo linha de requisição com ID: {}", id);
        try {
            if (service.getLinhaRequisicaoById(id) == null) {
                logger.warn("Linha de requisição com ID {} não encontrada", id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Exame não encontrado");
            }
            service.deleteLinhaRequisicao(id);
            logger.info("Linha de requisição com ID {} excluída com sucesso", id);
            return ResponseEntity.ok("Exame excluído com sucesso");
        } catch (Exception e) {
            logger.error("Erro ao excluir linha de requisição com ID {}: {}", id, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao excluir exame: " + e.getMessage());
        }
    }

    @GetMapping("/all/requisicao/{requisicaoExameId}")
    public ResponseEntity<List<LinhaRequisicaoExame>> getLinhasByRequisicaoId(@PathVariable long requisicaoExameId) {
        logger.info("Buscando linhas de requisição para requisicaoExameId: {}", requisicaoExameId);
        try {
            List<LinhaRequisicaoExame> linhas = service.listarLinhasPorRequisicaoId(requisicaoExameId);
            logger.info("Encontradas {} linhas para requisicaoExameId: {}", linhas.size(), requisicaoExameId);
            return ResponseEntity.ok(linhas);
        } catch (Exception e) {
            logger.error("Erro ao buscar linhas para requisicaoExameId {}: {}", requisicaoExameId, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
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
