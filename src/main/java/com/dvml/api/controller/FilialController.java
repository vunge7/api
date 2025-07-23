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

    // 🔹 Listar todas as filiais (com dados completos)
    @GetMapping("/all")
    public List<FilialDTO> getAllFiliais() {
        return service.getAllFilials();
    }

    // 🔹 Buscar uma filial pelo ID
    @GetMapping("/{id}")
    public FilialDTO getFilialById(@PathVariable Long id) {
        return service.getFilialById(id);
    }

    // 🔹 Listar apenas os nomes das filiais
    @GetMapping("/names")
    public List<FilialDTO> getFilialNames() {
        return service.listarNomesFiliais();
    }

    // 🔹 Criar uma nova filial
    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public FilialDTO create(@RequestBody @Valid FilialDTO filialDTO) {
        return service.create(filialDTO);
    }

    // 🔹 Editar uma filial existente
    @PutMapping("/edit")
    @ResponseStatus(HttpStatus.OK)
    public void update(@RequestBody @Valid FilialDTO filialDTO) {
        service.update(filialDTO);
    }

    // 🔹 Deletar uma filial pelo ID
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long id) {
        service.delete(id);
    }
}
