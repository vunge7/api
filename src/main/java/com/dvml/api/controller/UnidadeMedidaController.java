package com.dvml.api.controller;

import com.dvml.api.entity.UnidadeMedidas;
import com.dvml.api.service.UnidadeMedidaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class UnidadeMedidaController {
    @Autowired
    private UnidadeMedidaService service;


    @GetMapping("unidade/all")
    public List<UnidadeMedidas> getAllUnidadeMedidas(){
        return service.listarTodasUnidadeMedidas();
    }

    @GetMapping("unidade/{id}")
    public UnidadeMedidas getAllUnidadeMedidasId(@PathVariable long id){
        return service.getUnidadeMedidasById(id);
    }

    @PostMapping("unidade/add")
    @ResponseStatus(code = HttpStatus.CREATED)
    public UnidadeMedidas adicionar(@RequestBody @Valid UnidadeMedidas unidadeMedidas){
        return service.criar(unidadeMedidas);
    }
    @PutMapping("unidade/edit")
    @ResponseStatus(code = HttpStatus.CREATED)
    public void updateUnidadeMedida(@RequestBody @Valid UnidadeMedidas unidadeMedidas){
        service.update(unidadeMedidas);}

    @DeleteMapping("unidade/{id}")
    public void deleteUnidadeMedida(@PathVariable long id) {
        if (service.getUnidadeMedidasById(id) != null) {
            service.deleteUnidadeMedidas(id);
        } else {
            System.out.println("ERRO...");
        }

    }
}



