package com.dvml.api.controller;

import com.dvml.api.entity.Funcao;
import com.dvml.api.service.FuncaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
public class FuncaoController {

    @Autowired
    private FuncaoService service;

    @GetMapping("funcao/all")
    public List<Funcao> getAllFuncao() {
        return service.listarTodasFuncoes();
    }

    @GetMapping("funcao/{id}")
    public Funcao getFuncaoById(@PathVariable long id) {
        return service.getFuncaoById(id);
    }

    @PostMapping("funcao/add")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Map<String, Object>> adicionar(@RequestBody @Valid Funcao funcao) {
        return service.criar(funcao);
    }

    @PutMapping("funcao/edit")
    @ResponseStatus(HttpStatus.CREATED)
    public void updateFuncao(@RequestBody @Valid Funcao funcao) {
        service.update(funcao);
    }

    @DeleteMapping("funcao/{id}")
    public void deleteFuncao(@PathVariable long id) {
        if (service.getFuncaoById(id) != null) {
            service.deleteFuncao(id);
        } else {
            System.out.println("Função não encontrada para exclusão.");
        }
    }
}
