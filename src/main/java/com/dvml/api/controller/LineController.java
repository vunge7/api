package com.dvml.api.controller;


import com.dvml.api.entity.Line;
import com.dvml.api.service.LineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class LineController {
    @Autowired
    private LineService service;

    @GetMapping("line/all")
    public List<Line> getAllLine() {
        return service.listarTodasLinhas();
    }

    @GetMapping("line/{id}")
    public Line getAllLineById(@PathVariable long id) {
        return service.getLineById(id);
    }

    @PostMapping("line/add")
    @ResponseStatus(code = HttpStatus.CREATED)
    public ResponseEntity<String> adicionar(@RequestBody @Valid Line line){
        return service.criar(line);
    }

    @PutMapping("/line/edit")
    @ResponseStatus(code = HttpStatus.CREATED)
    public void updateLine(@RequestBody @Valid Line line){
        service.update(line);}

    @DeleteMapping("/line/{id}")
    public void deleteLine(@PathVariable long id) {
        if (service.getLineById(id) != null) {
            service.deleteLine(id);
        } else {
            System.out.println("ERRO...");
        }

    }
}

