package com.dvml.api.controller;

import com.dvml.api.dto.PacienteEmpresaDTO;
import com.dvml.api.entity.PacienteEmpresa;
import com.dvml.api.service.PacienteEmpresaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/paciente-empresa")
public class PacienteEmpresaController {

    @Autowired
    private PacienteEmpresaService service;

    @GetMapping("/{id}")
    public PacienteEmpresaDTO getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @GetMapping("/all")
    public List<PacienteEmpresaDTO> getAll() {
        return service.listarTodos();
    }

    @PostMapping("/add")
    @ResponseStatus(code = HttpStatus.CREATED)
    public PacienteEmpresaDTO criar(@RequestBody PacienteEmpresaDTO dto) {
        return service.criar(dto);
    }

    @PutMapping("/edit")
    public PacienteEmpresaDTO atualizar(@RequestBody PacienteEmpresaDTO dto) {
        return service.atualizar(dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}