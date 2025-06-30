package com.dvml.api.controller;

import com.dvml.api.dto.ConsultaSimpleDTO;
import com.dvml.api.dto.PacienteDTO;
import com.dvml.api.entity.Consulta;
import com.dvml.api.entity.Paciente;
import com.dvml.api.service.ConsultaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ConsultaController {
    @Autowired
    ConsultaService service;
    @GetMapping("/consulta/{id}")
    public ConsultaSimpleDTO getConsultaById(@PathVariable long id){
        return service.getConsultaById(id);
    }

    @GetMapping("/consulta/{idInscricao}/{estado}")
    public ConsultaSimpleDTO getConsultaById(@PathVariable long idInscricao, @PathVariable String estado){
        return service.getConsultaByEstadoAndIdInscricao(estado, idInscricao);
    }

    @GetMapping("/consulta/all")
    public List<ConsultaSimpleDTO> getConsultas(){
        return service.listarTodos();
    }

    @PostMapping("/consulta/add")
    @ResponseStatus(code = HttpStatus.CREATED)
    public Consulta criarConsulta(@RequestBody Consulta consulta){
        return service.adicionar(consulta);
    }

    @PutMapping("/consulta/edit")
    @ResponseStatus(code = HttpStatus.CREATED)
    public void updatePaciente(@RequestBody Consulta consulta){
        service.update(consulta);
    }
}
