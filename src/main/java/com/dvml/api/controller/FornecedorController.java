package com.dvml.api.controller;

import com.dvml.api.dto.FornecedorDTO;
import com.dvml.api.entity.Fornecedor;
import com.dvml.api.service.FornecedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/fornecedor")
public class FornecedorController {

    @Autowired
    private FornecedorService service;

    @GetMapping("/all")
    public List<Fornecedor> getAllFornecedores() {
        return service.listarTodosFornecedores();
    }

    @GetMapping("/{id}")
    public Fornecedor getFornecedorById(@PathVariable long id) {
        return service.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Fornecedor com ID " + id + " não encontrado"));
    }

    @PostMapping("/add")
    @ResponseStatus(code = HttpStatus.CREATED)
    public Fornecedor adicionar(@RequestBody @Valid FornecedorDTO fornecedorDTO) {
        ResponseEntity<?> response = service.cadastrarFornecedor(fornecedorDTO);
        if (response.getStatusCode() == HttpStatus.OK) {
            return (Fornecedor) response.getBody();
        }
        throw new IllegalArgumentException("Erro ao cadastrar fornecedor: " + response.getBody());
    }

    @PutMapping("/edit")
    @ResponseStatus(code = HttpStatus.CREATED)
    public void updateFornecedor(@RequestBody @Valid FornecedorDTO fornecedorDTO) {
        if (fornecedorDTO.getId() == null) {
            throw new IllegalArgumentException("ID do fornecedor é obrigatório para atualização");
        }
        ResponseEntity<String> response = service.editarFornecedor(fornecedorDTO.getId(), fornecedorDTO);
        if (response.getStatusCode() != HttpStatus.OK) {
            throw new IllegalArgumentException("Erro ao atualizar fornecedor: " + response.getBody());
        }
    }

    @DeleteMapping("/{id}")
    public void deleteFornecedor(@PathVariable long id) {
        if (service.findById(id).isPresent()) {
            service.deleteFornecedor(id);
        } else {
            throw new IllegalArgumentException("Fornecedor com ID " + id + " não encontrado");
        }
    }
}