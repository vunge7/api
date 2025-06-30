package com.dvml.api.controller;

import com.dvml.api.entity.Empresa;

import com.dvml.api.service.EmpresaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class EmpresaController {

    @Autowired
    private EmpresaService service;


    @GetMapping("empresa/all")
    public List<Empresa> getAllEmpresa(){
        return service.listarTodasEmpresa();
    }

    @GetMapping("empresa/{id}")
    public Empresa getAllEmpresaId(@PathVariable long id){
        return service.getEmpresaById(id);
    }
    @PostMapping("empresa/add")
    @ResponseStatus(code = HttpStatus.CREATED)
    public  Empresa adicionar(@RequestBody Empresa empresa){
        System.out.println("Nome: " +empresa.getNome());
        System.out.println("Telefone: " +empresa.getTelefone());
        System.out.println("Nif: " +empresa.getNif());
        return service.criar(empresa);
    }
    @PutMapping("/empresa/edit")
    @ResponseStatus(code = HttpStatus.CREATED)
    public void updatEmpresa(@RequestBody @Valid Empresa empresa){
        service.update(empresa);}

    @DeleteMapping("/empresa/{id}")
    public void deleteProduct(@PathVariable long id) {
        if (service.getEmpresaById(id) != null) {
            service.deletEmpresa(id);
        } else {
            System.out.println("ERRO...");
        }

    }
}
