package com.dvml.api.controller;


import com.dvml.api.entity.Pessoa;
import com.dvml.api.service.PessoaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class PessoaController {

    @Autowired
    private PessoaService service;

    @GetMapping("pessoa/all")
    public List<Pessoa> getAllPessoas() {
        return service.listarTodasPessoa();
    }

    @GetMapping("pessoa/{id}")
    public Pessoa getAllpessoaById(@PathVariable long id) {
        return service.getPessoaById(id);
    }


    @GetMapping("pessoa/nif/{nif}")
    public Pessoa getAllpessoaByNif(@PathVariable String nif) {

        return service.getPessoaByNif(nif);
    }

    @PostMapping("pessoa/add")
    @ResponseStatus(code = HttpStatus.CREATED)
    public Pessoa adicionar(@RequestBody @Valid Pessoa pessoa) {
        return service.criar(pessoa);
    }

    @PutMapping("pessoa/edit")
    @ResponseStatus(code = HttpStatus.CREATED)
    public Pessoa updatePessoa(@RequestBody @Valid Pessoa pessoa) {
        return service.update(pessoa);
    }

    @DeleteMapping("pessoa/{id}")
    public void deletePessoa(@PathVariable long id) {
        if (service.getPessoaById(id) != null) {
            service.deletePessoa(id);
        } else {
            System.out.println("ERRO...");
        }

    }
}
