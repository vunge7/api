package com.dvml.api.controller;

import com.dvml.api.dto.PacienteDTO;
import com.dvml.api.entity.Paciente;
import com.dvml.api.service.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping ("/paciente")
public class PacienteController {
    @Autowired
    PacienteService service;

    @GetMapping("/{id}")
    public PacienteDTO getPacienteById(@PathVariable long id){
        return service.getPacienteById(id);
    }

    @GetMapping("/all")
    public List<PacienteDTO> getPacientes(){
        return service.listarTodos();
    }

    @PostMapping("/add")
    @ResponseStatus(code = HttpStatus.CREATED)
    public Paciente criarPaciente(@RequestBody Paciente paciente){
        return service.adicionar(paciente);
    }

    @PutMapping("/edit")
    @ResponseStatus(code = HttpStatus.CREATED)
    public Paciente updatePaciente(@RequestBody Paciente paciente){
       return  service.update(paciente);
    }
}
