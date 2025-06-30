package com.dvml.api.controller;

import com.dvml.api.dto.FilialDTO;
import com.dvml.api.service.FilialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/filial")
public class FilialController {

    @Autowired
    private FilialService service;

    @GetMapping("/all")
    public List<FilialDTO> getAllFiliais() {
        return service.getAllFilials();
    }

    @GetMapping("/{id}")
    public FilialDTO getFilialById(@PathVariable Long id) {
        return service.getFilialById(id);
    }

    @GetMapping("/names")
    public List<FilialDTO> getFilialNames() {
        return service.listarNomesFiliais();
    }

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public FilialDTO create(@RequestBody @Valid FilialDTO filialDTO) {
        return service.create(filialDTO);
    }

    @PutMapping("/edit")
    @ResponseStatus(HttpStatus.OK)
    public void update(@RequestBody @Valid FilialDTO filialDTO) {
        service.update(filialDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long id) {
        service.delete(id);
    }
}