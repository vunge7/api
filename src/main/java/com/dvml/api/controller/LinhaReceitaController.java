package com.dvml.api.controller;


import com.dvml.api.entity.LinhaReceita;
import com.dvml.api.service.LinhaReceitaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class LinhaReceitaController {
    @Autowired
    private LinhaReceitaService service;

    @GetMapping("linhareceita/all")
    public List<LinhaReceita> getAllLinhaReceita() {
        return service.listarTodasLinhasReceitas();
    }
    @GetMapping("linhareceita/{id}")
    public LinhaReceita getAllLinhaReceitaById(@PathVariable long id) {
        return service.getLinhaReceitaById(id);
    }

    @PostMapping("linhareceita/add")
    @ResponseStatus(code = HttpStatus.CREATED)
    public ResponseEntity<String> adicionar(@RequestBody @Valid LinhaReceita linhareceita){
        return service.criar(linhareceita);
    }

    @PutMapping("linhareceita/edit")
    @ResponseStatus(code = HttpStatus.CREATED)
    public void updateLinhaReceita(@RequestBody @Valid LinhaReceita linhareceita){
        service.update(linhareceita);}

    @DeleteMapping("linhareceita/{id}")
    public void deletelinhareceita(@PathVariable long id) {
        if (service.getLinhaReceitaById(id) != null) {
            service.deleteLinhaReceita(id);
        } else {
            System.out.println("ERRO...");
        }
    }
}

