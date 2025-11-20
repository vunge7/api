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

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/produto")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    // ============================================================
    // CRIAR PRODUTO
    // ============================================================

    // 🔹 Criar um novo produto (JSON puro – compatível com o que já existia)
    @PostMapping(
            value = "/add",
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<String> createJson(@RequestBody @Valid ProdutoDTO produtoDTO) {
        return produtoService.criar(produtoDTO);
    }

    // 🔹 Criar um novo produto com imagem (multipart/form-data)
    // Compatível com envio via FormData no frontend
    @PostMapping(
            value = "/add",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<String> createComImagem(
            @RequestParam String productType,
            @RequestParam String productCode,
            @RequestParam String productGroup,
            @RequestParam String productDescription,
            @RequestParam(required = false) String unidadeMedida,
            @RequestParam(required = false) BigDecimal preco,
            @RequestParam(required = false) BigDecimal taxIva,
            @RequestParam(required = false) BigDecimal finalPrice,
            @RequestParam(required = false, defaultValue = "true") boolean status,
            @RequestParam(required = false) Long productTypeId,
            @RequestParam(required = false) Long productGroupId,
            @RequestParam(required = false) Long unidadeMedidaId,
            @RequestParam(required = false) Long produtoPaiId,
            @RequestParam(required = false) Long empresaId,
            @RequestParam(required = false) String intervaloReferencia,
            @RequestParam(value = "imagem", required = false) MultipartFile imagem
    ) {
        ProdutoDTO dto = new ProdutoDTO();
        dto.setProductType(productType);
        dto.setProductCode(productCode);
        dto.setProductGroup(productGroup);
        dto.setProductDescription(productDescription);
        dto.setUnidadeMedida(unidadeMedida);

        dto.setPreco(preco != null ? preco : BigDecimal.ZERO);
        dto.setTaxIva(taxIva != null ? taxIva : BigDecimal.ZERO);
        dto.setFinalPrice(finalPrice != null ? finalPrice : BigDecimal.ZERO);

        dto.setStatus(status);
        dto.setProductTypeId(productTypeId);
        dto.setProductGroupId(productGroupId);
        dto.setUnidadeMedidaId(unidadeMedidaId);
        dto.setProdutoPaiId(produtoPaiId);
        dto.setEmpresaId(empresaId);
        dto.setIntervaloReferencia(intervaloReferencia);

        return produtoService.criar(dto, imagem);
    }

    // ============================================================
    // EDITAR PRODUTO
    // ============================================================

    // 🔹 Atualizar produto (JSON puro – compatível com o que já existia)
    @PutMapping(
            value = "/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<String> updateJson(
            @PathVariable Long id,
            @RequestBody @Valid ProdutoDTO produtoDTO
    ) {
        return produtoService.update(id, produtoDTO);
    }

    // 🔹 Atualizar produto com imagem (multipart/form-data)
    // Compatível com o FormData que o frontend usa em NovoProduto/ListarProduto
    @PutMapping(
            value = "/{id}",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<String> updateComImagem(
            @PathVariable Long id,
            @RequestParam String productType,
            @RequestParam String productCode,
            @RequestParam String productGroup,
            @RequestParam String productDescription,
            @RequestParam(required = false) String unidadeMedida,
            @RequestParam(required = false) BigDecimal preco,
            @RequestParam(required = false) BigDecimal taxIva,
            @RequestParam(required = false) BigDecimal finalPrice,
            @RequestParam(required = false, defaultValue = "true") boolean status,
            @RequestParam(required = false) Long productTypeId,
            @RequestParam(required = false) Long productGroupId,
            @RequestParam(required = false) Long unidadeMedidaId,
            @RequestParam(required = false) Long produtoPaiId,
            @RequestParam(required = false) Long empresaId,
            @RequestParam(required = false) String intervaloReferencia,
            @RequestParam(value = "imagem", required = false) MultipartFile imagem
    ) {
        ProdutoDTO dto = new ProdutoDTO();
        dto.setProductType(productType);
        dto.setProductCode(productCode);
        dto.setProductGroup(productGroup);
        dto.setProductDescription(productDescription);
        dto.setUnidadeMedida(unidadeMedida);

        dto.setPreco(preco != null ? preco : BigDecimal.ZERO);
        dto.setTaxIva(taxIva != null ? taxIva : BigDecimal.ZERO);
        dto.setFinalPrice(finalPrice != null ? finalPrice : BigDecimal.ZERO);

        dto.setStatus(status);
        dto.setProductTypeId(productTypeId);
        dto.setProductGroupId(productGroupId);
        dto.setUnidadeMedidaId(unidadeMedidaId);
        dto.setProdutoPaiId(produtoPaiId);
        dto.setEmpresaId(empresaId);
        dto.setIntervaloReferencia(intervaloReferencia);

        return produtoService.update(id, dto, imagem);
    }

    // ============================================================
    // STATUS / ATIVAR / DESATIVAR / DELETE
    // ============================================================

    // 🔹 Ativar ou desativar um produto (status lógico)
    @PatchMapping("/{id}/status")
    public ResponseEntity<String> delete(@PathVariable Long id, @RequestParam boolean status) {
        return produtoService.deleteProduct(id, status);
    }

    // 🔹 Ativar um produto
    @PatchMapping("/{id}/ativar")
    public ResponseEntity<String> activate(@PathVariable Long id) {
        return produtoService.activateProduct(id);
    }

    // 🔹 Desativar um produto
    @PatchMapping("/{id}/desativar")
    public ResponseEntity<String> deactivate(@PathVariable Long id) {
        return produtoService.deactivateProduct(id);
    }

    // 🔹 Excluir permanentemente um produto
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePermanently(@PathVariable Long id) {
        return produtoService.deletePermanently(id);
    }

    // ============================================================
    // LISTAGENS / CONSULTAS
    // ============================================================

    // 🔹 Listar todos os produtos
    @GetMapping("/all")
    public ResponseEntity<List<ProdutoDTO>> findAll() {
        return ResponseEntity.ok(produtoService.listarTodosProdutos());
    }

    // 🔹 Buscar um produto pelo ID
    @GetMapping("/{id}")
    public ResponseEntity<ProdutoDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(
                produtoService.convertEntityToDto(produtoService.getProdutoById(id))
        );
    }

    // 🔹 Listar produtos por grupo
    @GetMapping("/grupo/{grupoId}")
    public ResponseEntity<List<Produto>> findByGrupo(@PathVariable Long grupoId) {
        return ResponseEntity.ok(produtoService.listarProdutosPorGrupo(grupoId));
    }

    // 🔹 Listar produtos por tipo
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