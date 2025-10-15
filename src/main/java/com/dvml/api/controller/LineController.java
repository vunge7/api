package com.dvml.api.controller;


import com.dvml.api.entity.Line;
import com.dvml.api.service.LineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping ("/line")
public class LineController {
    @Autowired
    private LineService service;

    @GetMapping("/all")
    public List<Line> getAllLine() {
        return service.listarTodasLinhas();
    }

    @GetMapping("/{id}")
    public Line getAllLineById(@PathVariable long id) {
        return service.getLineById(id);
    }

    @PostMapping("/add")
    @ResponseStatus(code = HttpStatus.CREATED)
    public ResponseEntity<String> adicionar(@RequestBody @Valid Line line){
        return service.criar(line);
    }

    @PutMapping("/edit")
    @ResponseStatus(code = HttpStatus.CREATED)
    public void updateLine(@RequestBody @Valid Line line){
        service.update(line);}

    @DeleteMapping("/{id}")
    public void deleteLine(@PathVariable long id) {
        if (service.getLineById(id) != null) {
            service.deleteLine(id);
        } else {
            System.out.println("ERRO...");
        }

    }
}

