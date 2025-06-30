package com.dvml.api.controller;

import com.dvml.api.entity.LinhaGasto;
import com.dvml.api.service.LinhaGastoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class LinhaGastoController {
    @Autowired
    private LinhaGastoService service;

    @GetMapping("linhagasto/all")
    public List<LinhaGasto> getAllLine() {
        return service.listarTodasLinhas();
    }

    @GetMapping("linhagasto/gasto/{id}")
    public List<LinhaGasto> getAllLine(@PathVariable long id) {
        return service.listarLinhasGastoByGastoId(id);
    }

    @GetMapping("linhagasto/{id}")
    public LinhaGasto getAllLineById(@PathVariable long id) {
        return service.getLineById(id);
    }

    @PostMapping("linhagasto/add")
    @ResponseStatus(code = HttpStatus.CREATED)
    public ResponseEntity<String> adicionar(@RequestBody @Valid LinhaGasto linha) {
        return service.criar(linha);
    }

    @PutMapping("linhagasto/edit")
    @ResponseStatus(code = HttpStatus.CREATED)
    public void updateLine(@RequestBody @Valid LinhaGasto linha) {
        service.update(linha);
    }

    @DeleteMapping("linhagasto/{id}")
    public void deleteLine(@PathVariable long id) {
        if (service.getLineById(id) != null) {
            service.deleteLinhaGasto(id);
        } else {
            System.out.println("ERRO...");
        }
    }
    @DeleteMapping("linhagasto/gasto/{id}")
    public void deleteLinePeloId(@PathVariable long id) {
        service.deleteLinhaPeloGasto(id);
    }
}

