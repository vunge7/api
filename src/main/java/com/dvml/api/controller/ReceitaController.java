package com.dvml.api.controller;


import com.dvml.api.entity.Receita;
import com.dvml.api.service.ReceitaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class ReceitaController {
    @Autowired
    private ReceitaService service;

    @GetMapping("receita/all")
    public List<Receita> getAllReceita() {
        return service.listarTodasReceitas();
    }
    @GetMapping("receita/{id}")
    public Receita getAllReceitaById(@PathVariable long id) {
        return service.getReceitaById(id);
    }

    @PostMapping("receita/add")
    @ResponseStatus(code = HttpStatus.CREATED)
    public Receita adicionar(@RequestBody @Valid Receita receita){
        return service.criar(receita);
    }

    @PutMapping("receita/edit")
    @ResponseStatus(code = HttpStatus.CREATED)
    public void updateReceita(@RequestBody @Valid Receita receita){
        service.update(receita);}

    @DeleteMapping("receita/{id}")
    public void deletereceita(@PathVariable long id) {
        if (service.getReceitaById(id) != null) {
            service.deleteReceita(id);
        } else {
            System.out.println("ERRO...");
        }
    }

    }


