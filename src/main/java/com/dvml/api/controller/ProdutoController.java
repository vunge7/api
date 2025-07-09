package com.dvml.api.controller;

import com.dvml.api.dto.ProdutoDTO;
import com.dvml.api.entity.Produto;
import com.dvml.api.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/produto")
@CrossOrigin(origins = "http://localhost:3000")
public class ProdutoController {
    @Autowired
    private ProdutoService service;

    @GetMapping("/all")
    public List<ProdutoDTO> getAllProduto() {
        return service.listarTodosProdutos();
    }

    @GetMapping("/{id}")
    public Produto getAllProdutoById(@PathVariable long id) {
        return service.getProdutoById(id);
    }

    @PostMapping(value = "/add", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> adicionar(
            @RequestParam("productType") String productType,
            @RequestParam("productTypeId") long productTypeId,
            @RequestParam("productCode") String productCode,
            @RequestParam("productGroup") String productGroup,
            @RequestParam("productGroupId") long productGroupId,
            @RequestParam("productDescription") String productDescription,
            @RequestParam("unidadeMedida") String unidadeMedida,
            @RequestParam("unidadeMedidaId") long unidadeMedidaId,
            @RequestParam("preco") String preco,
            @RequestParam("taxIva") String taxIva,
            @RequestParam("finalPrice") String finalPrice,
            @RequestParam("status") String status,
            @RequestParam(value = "imagem", required = true) MultipartFile imagem) {
        boolean statusBool = status.equals("1");
        return service.criar(
                productType,
                productTypeId,
                productCode,
                productGroup,
                productGroupId,
                productDescription,
                unidadeMedida,
                unidadeMedidaId,
                new BigDecimal(preco),
                new BigDecimal(taxIva),
                new BigDecimal(finalPrice),
                statusBool,
                imagem);
    }

    @PutMapping(value = "/edit", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> updateProduto(
            @RequestParam("id") long id,
            @RequestParam("productType") String productType,
            @RequestParam("productTypeId") long productTypeId,
            @RequestParam("productCode") String productCode,
            @RequestParam("productGroup") String productGroup,
            @RequestParam("productGroupId") long productGroupId,
            @RequestParam("productDescription") String productDescription,
            @RequestParam("unidadeMedida") String unidadeMedida,
            @RequestParam("unidadeMedidaId") long unidadeMedidaId,
            @RequestParam("preco") String preco,
            @RequestParam("taxIva") String taxIva,
            @RequestParam("finalPrice") String finalPrice,
            @RequestParam("status") String status,
            @RequestParam(value = "imagem", required = false) MultipartFile imagem) {
        boolean statusBool = status.equals("1");
        return service.update(
                id,
                productType,
                productTypeId,
                productCode,
                productGroup,
                productGroupId,
                productDescription,
                unidadeMedida,
                unidadeMedidaId,
                new BigDecimal(preco),
                new BigDecimal(taxIva),
                new BigDecimal(finalPrice),
                statusBool,
                imagem);
    }

    @PutMapping("/del")
    public ResponseEntity<String> deleteProduct(@RequestBody Map<String, Object> request) {
        long id = Long.parseLong(request.get("id").toString());
        boolean status = Boolean.parseBoolean(request.get("status").toString());
        return service.deleteProduct(id, status);
    }
    @GetMapping("/all/grupo/{id}")
    public List<Produto> getAllProdutosPorGrupo(@PathVariable long id) {
        return service.listarProdutosPorGrupo(id);
    }

    @GetMapping("/imagens/{nome}")
    public ResponseEntity<Resource> getImagem(@PathVariable String nome) throws MalformedURLException {
        Path filePath = Paths.get("uploads/produtos").resolve(nome).normalize();
        Resource resource = new UrlResource(filePath.toUri());
        if (resource.exists()) {
            return ResponseEntity.ok()
                    .header("Content-Disposition", "inline; filename=\"" + resource.getFilename() + "\"")
                    .body(resource);
        }
        return ResponseEntity.notFound().build();
    }
}