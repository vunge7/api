package com.dvml.api.controller;


import com.dvml.api.entity.LinhaAgenda;
import com.dvml.api.service.LinhaAgendaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
@RestController
public class LinhaAgendaController {
    @Autowired
    private LinhaAgendaService service;


    @GetMapping("linhaagenda/all")
    public List<LinhaAgenda> getAllLinhaAgenda(){
        return service.listarTodasLinhasAgenda();
    }

    @GetMapping("linhaagenda/{id}")
    public LinhaAgenda getAllLinhaAgendaId(@PathVariable long id){
        return service.getLinhaAgendaById(id);
    }

    @PostMapping("linhaagenda/add")
    @ResponseStatus(code = HttpStatus.CREATED)
    public  LinhaAgenda adicionar(@RequestBody @Valid LinhaAgenda linhaagenda){
        return service.criar(linhaagenda);
    }
    @PutMapping("linhaagenda/edit")
    @ResponseStatus(code = HttpStatus.CREATED)
    public void updateLinhaAgenda(@RequestBody @Valid LinhaAgenda linhaagenda){
        service.update(linhaagenda);}

    @DeleteMapping("linhaagenda/{id}")
    public void deletePessoa(@PathVariable long id) {
        if (service.getLinhaAgendaById(id) != null) {
            service.deleteLinhaAgenda(id);
        } else {
            System.out.println("ERRO...");
        }

    }

}



