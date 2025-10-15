package com.dvml.api.controller;


import com.dvml.api.entity.Pessoa;
import com.dvml.api.service.PessoaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping ("/pessoa")
public class PessoaController {

    @Autowired
    private PessoaService service;

    @GetMapping("/all")
    public List<Pessoa> getAllPessoas() {
        return service.listarTodasPessoa();
    }

    @GetMapping("/{id}")
    public Pessoa getAllpessoaById(@PathVariable long id) {
        return service.getPessoaById(id);
    }


    @GetMapping("/nif/{nif}")
    public Pessoa getAllpessoaByNif(@PathVariable String nif) {

        return service.getPessoaByNif(nif);
    }

    @PostMapping("/add")
    @ResponseStatus(code = HttpStatus.CREATED)
    public Pessoa adicionar(@RequestBody @Valid Pessoa pessoa) {
        return service.criar(pessoa);
    }

    @PutMapping("/edit")
    @ResponseStatus(code = HttpStatus.CREATED)
    public Pessoa updatePessoa(@RequestBody @Valid Pessoa pessoa) {
        return service.update(pessoa);
    }

    @DeleteMapping("/{id}")
    public void deletePessoa(@PathVariable long id) {
        if (service.getPessoaById(id) != null) {
            service.deletePessoa(id);
        } else {
            System.out.println("ERRO...");
        }

    }
}
