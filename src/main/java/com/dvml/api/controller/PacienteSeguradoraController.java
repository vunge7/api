package com.dvml.api.controller;


import com.dvml.api.entity.PacienteSeguradora;
import com.dvml.api.service.PacienteSeguradoraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import javax.validation.Valid;
import java.util.List;

@RestController
public class PacienteSeguradoraController {
    @Autowired
    private PacienteSeguradoraService service;


    @GetMapping("pacienteSeguradora/all")
    public List<PacienteSeguradora> getAllPacientSeguradora(){
        return service.listarTodosPacientesSeguradoras();
    }


    @GetMapping("pacienteSeguradora/all/{id}")
    public List<PacienteSeguradora> getAllPacientSeguradoraByPaciente(@PathVariable long id){
        return service.getAllSeguradorasByIdPaciente(id);
    }



    @GetMapping("pacienteSeguradora/{id}")
    public PacienteSeguradora getAllPacienteSeguradoraById(@PathVariable long id){
        return service.getPacienteSeguradoraById(id);
    }
    @PostMapping("pacienteSeguradora/add")
    @ResponseStatus(code = HttpStatus.CREATED)
    public ResponseEntity<String> adicionar(@RequestBody @Valid PacienteSeguradora pacienteSeguradora) {
        return service.criar(pacienteSeguradora);
    }

    @PutMapping("/pacienteSeguradora/edit")
    @ResponseStatus(code = HttpStatus.CREATED)
    public void updatPacienteseguradora(@RequestBody @Valid PacienteSeguradora pacienteSeguradora){
        service.update(pacienteSeguradora);}

    @DeleteMapping("/pacienteSeguradora/{id}")
    public void deleteProduct(@PathVariable long id) {
        if (service.getPacienteSeguradoraById(id) != null) {
            service.deletPacienteSeguradora(id);
        } else {
            System.out.println("ERRO...");
        }
    }
    }
