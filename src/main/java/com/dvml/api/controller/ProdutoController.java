package com.dvml.api.controller;

import com.dvml.api.dto.ProdutoArvoreDTO;
import com.dvml.api.dto.ProdutoDTO;
import com.dvml.api.entity.Produto;
import com.dvml.api.service.ProdutoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/produto")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    // 🔹 Criar um novo produto
    @PostMapping(value = "/add", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> create(
            @RequestParam("productType") String productType,
            @RequestParam("productCode") String productCode,
            @RequestParam("productGroup") String productGroup,
            @RequestParam("productDescription") String productDescription,
            @RequestParam("unidadeMedida") String unidadeMedida,
            @RequestParam("preco") BigDecimal preco,
            @RequestParam("taxIva") BigDecimal taxIva,
            @RequestParam("finalPrice") BigDecimal finalPrice,
            @RequestParam("productGroupId") Long productGroupId,
            @RequestParam("productTypeId") Long productTypeId,
            @RequestParam("unidadeMedidaId") Long unidadeMedidaId,
            @RequestParam(value = "produtoPaiId", required = false) Long produtoPaiId,
            @RequestParam(value = "status", required = false, defaultValue = "true") Boolean status,
            @RequestParam(value = "imagem", required = false) MultipartFile imagem
    ) {
        ProdutoDTO dto = new ProdutoDTO();
        dto.setProductType(productType);
        dto.setProductCode(productCode);
        dto.setProductGroup(productGroup);
        dto.setProductDescription(productDescription);
        dto.setUnidadeMedida(unidadeMedida);
        dto.setPreco(preco);
        dto.setTaxIva(taxIva);
        dto.setFinalPrice(finalPrice);
        dto.setProductGroupId(productGroupId);
        dto.setProductTypeId(productTypeId);
        dto.setUnidadeMedidaId(unidadeMedidaId);
        dto.setProdutoPaiId(produtoPaiId);
        dto.setStatus(status != null ? status : true);

        // Opcional: salvar imagem como Base64 ou apenas o nome do arquivo
        if (imagem != null && !imagem.isEmpty()) {
            try {
                String fileName = imagem.getOriginalFilename();
                dto.setImagem(fileName);

// Opcional: salvar o arquivo no disco
                imagem.transferTo(new File("uploads/produtos/" + fileName));

            } catch (Exception e) {
                return ResponseEntity.badRequest().body("Erro ao processar imagem: " + e.getMessage());
            }
        }

        return produtoService.criar(dto);
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
    // 🔹 Listar produtos por Tipo
    @GetMapping("/tipo/{tipoId}")
    public ResponseEntity<List<Produto>> findByTipo(@PathVariable Long tipoId) {
        return ResponseEntity.ok(produtoService.listarProdutosPorTipo(tipoId));
    }


    // 🔹 Buscar a árvore de produtos (produto pai e seus filhos)
    @GetMapping("/{id}/arvore")
    public ResponseEntity<ProdutoArvoreDTO> getArvore(@PathVariable Long id) {
        return ResponseEntity.ok(produtoService.montarArvoreProduto(id));
    }
}
