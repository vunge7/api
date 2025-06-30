package com.dvml.api.controller;


import com.dvml.api.dto.ProdutoDTO;
import com.dvml.api.entity.Produto;
import com.dvml.api.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class ProdutoController {
    @Autowired
    private ProdutoService service;

    @GetMapping("produto/all")
    public List<ProdutoDTO> getAllProduto() {
        return service.listarTodosProdutos();
    }

    @GetMapping("produto/{id}")
    public Produto getAllProdutoById(@PathVariable long id) {
        return service.getProdutoById(id);
    }

    @PostMapping("produto/add")
    @ResponseStatus(code = HttpStatus.CREATED)
    public ResponseEntity<String> adicionar(@RequestBody @Valid Produto produto) {
        return service.criar(produto);
    }

    @PutMapping("produto/edit")
    @ResponseStatus(code = HttpStatus.CREATED)
    public void updateProduto(@RequestBody @Valid Produto produto) {
        service.update(produto);
    }

    @PutMapping("produto/del")
    public void deleteProduct(@RequestBody @Valid Produto produto) {
        {
            service.deleteProduct(produto);
        }

    }

    @GetMapping("produto/all/grupo/{id}")
    public List<Produto> getAllProdutosPorGrupo(@PathVariable long id) {
        return service.listarProdutosPorGrupo(id);
    }


}

