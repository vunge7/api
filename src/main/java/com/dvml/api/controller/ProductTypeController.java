package com.dvml.api.controller;


import com.dvml.api.entity.ProductType;
import com.dvml.api.service.ProductTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class ProductTypeController {
    @Autowired
   private ProductTypeService service;

    @GetMapping("producttype/all")
    public List<ProductType> getAllProduto() {
        return service.listarTodosTipos();
    }

    @GetMapping("producttype/{id}")
    public ProductType getAllProdutoById(@PathVariable long id) {
        return service.getProdutoById(id);
    }

    @PostMapping("producttype/add")
    @ResponseStatus(code = HttpStatus.CREATED)
    public ResponseEntity<String> adicionar(@RequestBody @Valid ProductType type){
        return service.criar(type);
    }

    @PutMapping("producttype/edit")
    @ResponseStatus(code = HttpStatus.CREATED)
    public void updateProduto(@RequestBody @Valid ProductType tytpe){
        service.update(tytpe);}

    @DeleteMapping("producttype/{id}")
    public void deleteProduct(@PathVariable long id) {
        if (service.getProdutoById(id) != null) {
            service.deleteType(id);
        } else {
            System.out.println("ERRO...");
        }

    }
}

