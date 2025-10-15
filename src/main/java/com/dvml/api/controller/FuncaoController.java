package com.dvml.api.controller;

import com.dvml.api.entity.Funcao;
import com.dvml.api.service.FuncaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/funcao")
public class FuncaoController {

    @Autowired
    private FuncaoService funcaoService;

    // Listar todas as funções
    @GetMapping("/all")
    public ResponseEntity<List<Funcao>> listarTodasFuncoes() {
        List<Funcao> funcoes = funcaoService.listarTodasFuncoes();
        return ResponseEntity.ok(funcoes);
    }

    // Buscar função por ID
    @GetMapping("/{id}")
    public ResponseEntity<Funcao> getFuncaoById(@PathVariable Long id) {
        Funcao funcao = funcaoService.getFuncaoById(id);
        return funcao != null ? ResponseEntity.ok(funcao) : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    // Criar nova função
    @PostMapping("/add")
    public ResponseEntity<Map<String, Object>> criarFuncao(@RequestBody Funcao funcao) {
        return funcaoService.criar(funcao);
    }

    // Atualizar função
    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> atualizarFuncao(@PathVariable Long id, @RequestBody Funcao funcao) {
        funcao.setId(id);
        return funcaoService.update(funcao);
    }

    // Deletar função
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deletarFuncao(@PathVariable Long id) {
        return funcaoService.deleteFuncao(id);
    }
}