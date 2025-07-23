package com.dvml.api.controller;

import com.dvml.api.dto.ProdutoArvoreDTO;
import com.dvml.api.dto.ProdutoDTO;
import com.dvml.api.entity.Produto;
import com.dvml.api.service.ProdutoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/produto")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    // 🔹 Criar um novo produto
    @PostMapping("/add")
    public ResponseEntity<String> create(@RequestBody @Valid ProdutoDTO produtoDTO) {
        return produtoService.criar(produtoDTO);
    }

    // 🔹 Atualizar um produto existente
    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable Long id, @RequestBody @Valid ProdutoDTO produtoDTO) {
        return produtoService.update(id, produtoDTO);
    }

    // 🔹 Ativar ou desativar um produto (status lógico)
    @PatchMapping("/{id}/status")
    public ResponseEntity<String> delete(@PathVariable Long id, @RequestParam boolean status) {
        return produtoService.deleteProduct(id, status);
    }

    // 🔹 Listar todos os produtos
    @GetMapping("/all")
    public ResponseEntity<List<ProdutoDTO>> findAll() {
        return ResponseEntity.ok(produtoService.listarTodosProdutos());
    }

    // 🔹 Buscar um produto pelo ID
    @GetMapping("/{id}")
    public ResponseEntity<ProdutoDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(produtoService.convertEntityToDto(produtoService.getProdutoById(id)));
    }

    // 🔹 Listar produtos por grupo
    @GetMapping("/grupo/{grupoId}")
    public ResponseEntity<List<Produto>> findByGrupo(@PathVariable Long grupoId) {
        return ResponseEntity.ok(produtoService.listarProdutosPorGrupo(grupoId));
    }

    // 🔹 Buscar a árvore de produtos (produto pai e seus filhos)
    @GetMapping("/{id}/arvore")
    public ResponseEntity<ProdutoArvoreDTO> getArvore(@PathVariable Long id) {
        return ResponseEntity.ok(produtoService.montarArvoreProduto(id));
    }
}
