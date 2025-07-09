package com.dvml.api.controller;

import com.dvml.api.entity.LinhaRequisicaoExame;
import com.dvml.api.service.LinhaRequisicaoExameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/linharequisicaoexame")
public class LinhaRequisicaoExameController {
    @Autowired
    private LinhaRequisicaoExameService service;

    @GetMapping("/all")
    public ResponseEntity<List<LinhaRequisicaoExame>> getAllReceita() {
        return ResponseEntity.ok(service.listarTodasLinhaRequisicoes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<LinhaRequisicaoExame> getAllLinhaRequisicaoById(@PathVariable long id) {
        LinhaRequisicaoExame exame = service.getLinhaRequisicaoById(id);
        if (exame == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(exame);
    }

    @PostMapping("/add")
    @ResponseStatus(code = HttpStatus.CREATED)
    public ResponseEntity<String> adicionar(@RequestBody @Valid LinhaRequisicaoExame linhaRequisicaoExame) {
        try {
            service.criar(linhaRequisicaoExame);
            return ResponseEntity.status(HttpStatus.CREATED).body("Exame criado com sucesso");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao criar exame: " + e.getMessage());
        }
    }

    @PutMapping("/edit")
    public ResponseEntity<String> updateLinhaRequisicao(@RequestBody @Valid LinhaRequisicaoExame linhaRequisicaoExame) {
        try {
            if (service.getLinhaRequisicaoById(linhaRequisicaoExame.getId()) == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Exame não encontrado");
            }
            service.update(linhaRequisicaoExame);
            return ResponseEntity.ok("Exame atualizado com sucesso");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao atualizar exame: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRequisicao(@PathVariable long id) {
        try {
            if (service.getLinhaRequisicaoById(id) == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Exame não encontrado");
            }
            service.deleteLinhaRequisicao(id);
            return ResponseEntity.ok("Exame excluído com sucesso");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao excluir exame: " + e.getMessage());
        }
    }

    @GetMapping("/all/requisicao/{requisicaoExameId}")
    public ResponseEntity<List<LinhaRequisicaoExame>> getLinhasByRequisicaoId(@PathVariable long requisicaoExameId) {
        try {
            List<LinhaRequisicaoExame> linhas = service.listarLinhasPorRequisicaoId(requisicaoExameId);
            return ResponseEntity.ok(linhas);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}