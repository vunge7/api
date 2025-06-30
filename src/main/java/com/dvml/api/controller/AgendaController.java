package com.dvml.api.controller;

import com.dvml.api.entity.Agenda;
import com.dvml.api.service.AgendaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class AgendaController {
    @Autowired
    private AgendaService service;


    @GetMapping("agenda/all")
    public List<Agenda> getAllAgenda(){
        return service.listarTodasAgenda();
    }

    @GetMapping("agenda/{id}")
    public Agenda getAllAgendaId(@PathVariable long id){
        return service.getAgendaById(id);
    }

    @PostMapping("agenda/add")
    @ResponseStatus(code = HttpStatus.CREATED)
    public  Agenda adicionar(@RequestBody @Valid Agenda agenda){
        return service.criar(agenda);
    }
    @PutMapping("agenda/edit")
    @ResponseStatus(code = HttpStatus.CREATED)
    public void updateAgenda(@RequestBody @Valid Agenda agenda){
        service.update(agenda);}

    @DeleteMapping("agenda/{id}")
    public void deleteAgenda(@PathVariable long id) {
        if (service.getAgendaById(id) != null) {
            service.deleteAgenda(id);
        } else {
            System.out.println("ERRO...");
        }

    }
}



