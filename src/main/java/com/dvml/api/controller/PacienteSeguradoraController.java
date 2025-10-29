package com.dvml.api.controller;


import com.dvml.api.entity.PacienteSeguradora;
import com.dvml.api.service.PacienteSeguradoraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping ("/pacienteSeguradora")
public class PacienteSeguradoraController {
    @Autowired
    private PacienteSeguradoraService service;


    @GetMapping("/all")
    public List<PacienteSeguradora> getAllPacientSeguradora(){
        return service.listarTodosPacientesSeguradoras();
    }


    @GetMapping("/all/{id}")
    public List<PacienteSeguradora> getAllPacientSeguradoraByPaciente(@PathVariable long id){
        return service.getAllSeguradorasByIdPaciente(id);
    }



    @GetMapping("/{id}")
    public PacienteSeguradora getAllPacienteSeguradoraById(@PathVariable long id){
        return service.getPacienteSeguradoraById(id);
    }
    @PostMapping("/add")
    public ResponseEntity<String> adicionar(@RequestBody @Valid PacienteSeguradora pacienteSeguradora) {
        try {
            service.criar(pacienteSeguradora);
            return ResponseEntity.status(HttpStatus.CREATED).body("Convênio criado com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro: " + e.getMessage());
        }
    }

    @PutMapping("/pacienteSeguradora/edit")
    @ResponseStatus(code = HttpStatus.CREATED)
    public void updatPacienteseguradora(@RequestBody @Valid PacienteSeguradora pacienteSeguradora){
        service.update(pacienteSeguradora);}

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable long id) {
        if (service.getPacienteSeguradoraById(id) != null) {
            service.deletPacienteSeguradora(id);
        } else {
            System.out.println("ERRO...");
        }
    }
    }
