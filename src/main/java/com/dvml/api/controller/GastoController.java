package com.dvml.api.controller;



import com.dvml.api.entity.Gasto;
import com.dvml.api.service.GastoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping ("/gasto")
public class GastoController {
    @Autowired
    private GastoService service;

    @GetMapping("/all")
    public List <Gasto> getAllGasto() {
        return service.listarTodosGasto();
    }

    @GetMapping("/{id}")
    public Gasto getAllGastoById(@PathVariable long id) {
        return service.getGastoById(id);
    }


    @PostMapping("/add")
    @ResponseStatus(code = HttpStatus.CREATED)
    public Gasto adicionar (@RequestBody  Gasto gasto){
        return service.criar(gasto);
    }
    @PutMapping("/edit")
    @ResponseStatus(code = HttpStatus.CREATED)
    public void updateGasto(@RequestBody @Valid Gasto gasto){
        service.update(gasto);}

    @DeleteMapping("/{id}")
    public void deleteGasto(@PathVariable long id) {
        if (service.getGastoById(id) != null) {
            service.deleteGasto(id);
        } else {
            System.out.println("ERRO...");
        }
    }
}
