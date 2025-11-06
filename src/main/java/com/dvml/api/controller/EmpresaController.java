package com.dvml.api.controller;

import com.dvml.api.dto.EmpresaDTO;
import com.dvml.api.entity.Empresa;
import com.dvml.api.service.EmpresaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/empresa")
public class EmpresaController {

    private final EmpresaService empresaService;

    @Autowired
    public EmpresaController(EmpresaService empresaService) {
        this.empresaService = empresaService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Empresa>> listarTodasEmpresas() {
        return ResponseEntity.ok(empresaService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmpresaDTO> getEmpresaById(@PathVariable Long id) {
        return empresaService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/filial/{id}")
    public ResponseEntity<EmpresaDTO> getFilialById(@PathVariable Long id) {
        return empresaService.findFilialById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/filial/all")
    public ResponseEntity<List<EmpresaDTO>> listarTodasFiliais() {
        return ResponseEntity.ok(empresaService.listarTodasFiliais());
    }

    @PostMapping("/add")
    public ResponseEntity<?> criarEmpresa(@Valid @RequestBody Empresa empresa) {
        try {
            if (empresa.getNome() == null || empresa.getNome().trim().isEmpty()) {
                return ResponseEntity.badRequest().body("Nome da empresa é obrigatório.");
            }
            if (empresa.getTipo() == null) {
                return ResponseEntity.badRequest().body("Tipo da empresa é obrigatório.");
            }
            Empresa nova = empresaService.save(empresa);
            return ResponseEntity.status(HttpStatus.CREATED).body(nova);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Erro ao cadastrar empresa: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Empresa> atualizarEmpresa(@PathVariable Long id, @RequestBody @Valid Empresa empresa) {
        try {
            return ResponseEntity.ok(empresaService.update(id, empresa));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("/arvore")
    public ResponseEntity<List<EmpresaDTO>> listarArvoreCompleta() {
        return ResponseEntity.ok(empresaService.listarArvoreCompleta());
    }

    // NOVO: Deletar empresa (bloqueia se tiver filiais)
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletarEmpresa(@PathVariable Long id) {
        try {
            empresaService.delete(id);
            return ResponseEntity.ok("Empresa deletada com sucesso.");
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getReason());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro inesperado ao deletar empresa: " + e.getMessage());
        }
    }

    // NOVO: Deletar empresa + todas as filiais (recursivo)
    @DeleteMapping("/{id}/cascade")
    public ResponseEntity<?> deletarEmpresaComFiliais(@PathVariable Long id) {
        try {
            empresaService.deleteCascade(id);
            return ResponseEntity.ok("Empresa e todas as filiais foram deletadas com sucesso.");
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getReason());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao deletar empresa e filiais: " + e.getMessage());
        }
    }
}