package com.dvml.api.controller;

import com.dvml.api.entity.ProductGroup;
import com.dvml.api.service.ProductGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping ("/productgroup")
public class ProductGroupController {
    @Autowired
    private ProductGroupService service;

    @GetMapping("/all")
    public List<ProductGroup> getAllProduto() {
        return service.listarTodosGrupos();
    }

    @PutMapping("/edit")
    @ResponseStatus(code = HttpStatus.CREATED)
    public void updateProduto(@RequestBody @Valid ProductGroup group) {
        service.update(group);
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable long id) {
        if (service.getProdutoById(id) != null) {
            service.deleteGroup(id);
        } else {
            System.out.println("ERRO...");
        }

    }

    @PostMapping("/add")
    @ResponseStatus(code = HttpStatus.CREATED)
    public ResponseEntity<String> adicionar(@RequestBody @Valid ProductGroup group) {
        return service.criar(group);
    }
}
