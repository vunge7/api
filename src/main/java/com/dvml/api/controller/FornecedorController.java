package com.dvml.api.controller;

import com.dvml.api.dto.FornecedorDTO;
import com.dvml.api.entity.Fornecedor;
import com.dvml.api.service.FornecedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/fornecedor")
public class FornecedorController {

    @Autowired
    private FornecedorService fornecedorService;

    // Listar todos os fornecedores
    @GetMapping("/all")
    public ResponseEntity<List<FornecedorDTO>> listarTodosFornecedores() {
        List<FornecedorDTO> fornecedores = fornecedorService.listarTodosFornecedores();
        return ResponseEntity.ok(fornecedores);
    }

    // Buscar fornecedor por ID
    @GetMapping("/{id}")
    public ResponseEntity<Fornecedor> getFornecedorById(@PathVariable Long id) {
        return fornecedorService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // Cadastrar novo fornecedor
    @PostMapping("/add")
    public ResponseEntity<?> cadastrarFornecedor(@RequestBody FornecedorDTO fornecedorDTO) {
        try {
            return fornecedorService.cadastrarFornecedor(fornecedorDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao cadastrar fornecedor");
        }
    }

    // Editar fornecedor
    @PutMapping("/{id}")
    public ResponseEntity<String> editarFornecedor(@PathVariable Long id, @RequestBody FornecedorDTO fornecedorDTO) {
        return fornecedorService.editarFornecedor(id, fornecedorDTO);
    }

    // Deletar fornecedor
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletarFornecedor(@PathVariable Long id) {
        return fornecedorService.deleteFornecedor(id);
    }
}