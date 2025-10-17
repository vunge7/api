package com.dvml.api.controller;

import com.dvml.api.dto.FilialDTO;
import com.dvml.api.service.FilialService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/filial")
public class FilialController {

    private static final Logger logger = LoggerFactory.getLogger(FilialController.class);

    @Autowired
    private FilialService filialService;

    // Listar todas as filiais
    @GetMapping("/all")
    public ResponseEntity<List<FilialDTO>> getAllFiliais() {
        logger.info("Requisição para listar todas as filiais");
        List<FilialDTO> filiais = filialService.getAllFilials();
        return ResponseEntity.ok(filiais);
    }

    // Listar apenas nomes das filiais
    @GetMapping("/nomes")
    public ResponseEntity<List<FilialDTO>> listarNomesFiliais() {
        logger.info("Requisição para listar nomes das filiais");
        List<FilialDTO> nomes = filialService.listarNomesFiliais();
        return ResponseEntity.ok(nomes);
    }

    // Buscar filial por ID
    @GetMapping("/{id}")
    public ResponseEntity<FilialDTO> getFilialById(@PathVariable Long id) {
        try {
            FilialDTO filial = filialService.getFilialById(id);
            return ResponseEntity.ok(filial);
        } catch (IllegalArgumentException e) {
            logger.error("Erro ao buscar filial: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // Criar nova filial
    @PostMapping("/add")
    public ResponseEntity<FilialDTO> criarFilial(@RequestBody FilialDTO filialDTO) {
        try {
            FilialDTO novaFilial = filialService.create(filialDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(novaFilial);
        } catch (IllegalArgumentException e) {
            logger.error("Erro ao criar filial: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    // Atualizar filial
    @PutMapping("/{id}")
    public ResponseEntity<FilialDTO> atualizarFilial(@PathVariable Long id, @RequestBody FilialDTO filialDTO) {
        try {
            filialDTO.setId(id);
            FilialDTO atualizada = filialService.update(filialDTO);
            return ResponseEntity.ok(atualizada);
        } catch (IllegalArgumentException e) {
            logger.error("Erro ao atualizar filial: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    // Deletar filial
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarFilial(@PathVariable Long id) {
        try {
            filialService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            logger.error("Erro ao deletar filial: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}