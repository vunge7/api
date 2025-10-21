package com.dvml.api.controller;

import com.dvml.api.dto.EmpresaDTO;
import com.dvml.api.entity.Empresa;
import com.dvml.api.service.EmpresaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/empresa")
public class EmpresaController {

    @Autowired
    private EmpresaService empresaService;

    // Listar todas as empresas ativas
    @GetMapping("/all")
    public ResponseEntity<List<Empresa>> listarTodasEmpresas() {
        List<Empresa> empresas = empresaService.findAll();
        return ResponseEntity.ok(empresas);
    }

    // Buscar empresa por ID (com filiais)
    @GetMapping("/{id}")
    public ResponseEntity<EmpresaDTO> getEmpresaById(@PathVariable Long id) {
        return empresaService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // ✅ NOVO ENDPOINT - Buscar filial específica
    @GetMapping("/filial/{id}")
    public ResponseEntity<EmpresaDTO> getFilialById(@PathVariable Long id) {
        return empresaService.findFilialById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // ✅ Listar todas as filiais
    @GetMapping("/filial/all")
    public ResponseEntity<List<EmpresaDTO>> listarTodasFiliais() {
        List<EmpresaDTO> filiais = empresaService.listarTodasFiliais();
        return ResponseEntity.ok(filiais);
    }


    // Criar nova empresa
    @PostMapping("/add")
    public ResponseEntity<Empresa> criarEmpresa(@RequestBody Empresa empresa) {
        try {
            Empresa novaEmpresa = empresaService.save(empresa);
            return ResponseEntity.status(HttpStatus.CREATED).body(novaEmpresa);
        } catch (org.springframework.web.server.ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).build();
        }
    }

    // Atualizar empresa
    @PutMapping("/{id}")
    public ResponseEntity<Empresa> atualizarEmpresa(@PathVariable Long id, @RequestBody Empresa empresa) {
        try {
            Empresa atualizada = empresaService.update(id, empresa);
            return ResponseEntity.ok(atualizada);
        } catch (org.springframework.web.server.ResponseStatusException e) {
            return ResponseEntity.status(e.getStatusCode()).build();
        }
    }

    // Listar árvore completa de empresas
    @GetMapping("/arvore")
    public ResponseEntity<List<EmpresaDTO>> listarArvoreCompleta() {
        List<EmpresaDTO> arvore = empresaService.listarArvoreCompleta();
        return ResponseEntity.ok(arvore);
    }
}
