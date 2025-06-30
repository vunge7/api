package com.dvml.api.controller;

import com.dvml.api.entity.Seguradora;
import com.dvml.api.service.SeguradoraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class SeguradoraController {
    @Autowired
    private SeguradoraService service;

    @GetMapping("seguradora/all")
    public List<Seguradora> getAllSeguradora(){
        return service.listarTodasSeguradoras();
    }

    @GetMapping("seguradora/{id}")
    public Seguradora getAllSeguradoraById(@PathVariable long id){
        return service.getSeguradoraById(id);
    }
    @PostMapping("seguradora/add")
    @ResponseStatus(code = HttpStatus.CREATED)
    public  Seguradora adicionar(@RequestBody Seguradora seguradora){
        System.out.println("Nome: " +seguradora.getNome());
        System.out.println("Telefone: " +seguradora.getTelefone());
        System.out.println("Nif: " +seguradora.getNif());
        return service.criar(seguradora);
    }
}
