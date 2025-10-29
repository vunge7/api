package com.dvml.api.controller;

import com.dvml.api.entity.Seguradora;
import com.dvml.api.service.SeguradoraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/seguradora")
public class SeguradoraController {

    @Autowired
    private SeguradoraService service;

    // Listar todas
    @GetMapping("/all")
    public List<Seguradora> getAllSeguradora() {
        return service.listarTodasSeguradoras();
    }

    // Buscar por ID
    @GetMapping("/{id}")
    public Seguradora getAllSeguradoraById(@PathVariable long id) {
        return service.getSeguradoraById(id);
    }

    // Criar nova
    @PostMapping("/add")
    @ResponseStatus(code = HttpStatus.CREATED)
    public Seguradora adicionar(@RequestBody Seguradora seguradora) {
        System.out.println("Nome: " + seguradora.getNome());
        System.out.println("Telefone: " + seguradora.getTelefone());
        System.out.println("Nif: " + seguradora.getNif());
        return service.criar(seguradora);
    }

    // Atualizar (PUT)
    @PutMapping("/{id}")
    public ResponseEntity<String> atualizar(@PathVariable long id, @RequestBody Seguradora seguradora) {
        return service.atualizar(id, seguradora);
    }

    // Deletar (DELETE)
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletar(@PathVariable long id) {
        return service.deletar(id);
    }
}