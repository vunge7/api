package com.dvml.api.controller;

import com.dvml.api.dto.FornecedorDTO;
import com.dvml.api.entity.Fornecedor;
import com.dvml.api.service.FornecedorService;
import jakarta.validation.Valid;
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
    public ResponseEntity<?> cadastrarFornecedor(@Valid @RequestBody FornecedorDTO fornecedorDTO) {
        try {
            FornecedorDTO savedFornecedor = fornecedorService.cadastrarFornecedor(fornecedorDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedFornecedor);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao cadastrar fornecedor: " + e.getMessage());
        }
    }

    // Editar fornecedor
    @PutMapping("/{id}")
    public ResponseEntity<?> editarFornecedor(@PathVariable Long id, @Valid @RequestBody FornecedorDTO fornecedorDTO) {
        try {
            FornecedorDTO updatedFornecedor = fornecedorService.editarFornecedor(id, fornecedorDTO);
            return ResponseEntity.ok(updatedFornecedor);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Fornecedor não encontrado: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro ao atualizar fornecedor: " + e.getMessage());
        }
    }

    // Deletar fornecedor
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletarFornecedor(@PathVariable Long id) {
        try {
            fornecedorService.deleteFornecedor(id);
            return ResponseEntity.ok("Fornecedor excluído com sucesso!");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Fornecedor não encontrado: " + e.getMessage());
        }
    }
}