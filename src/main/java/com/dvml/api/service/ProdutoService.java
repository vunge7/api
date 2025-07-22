package com.dvml.api.service;

import com.dvml.api.dto.ProdutoDTO;
import com.dvml.api.entity.Produto;
import com.dvml.api.repository.ProdutoRepository;
import com.dvml.api.util.FileUploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProdutoService {
    @Autowired
    private ProdutoRepository repo;

    // Métodos existentes (listarTodosProdutos, getProdutoById, etc.) permanecem iguais

    public List<ProdutoDTO> listarTodosProdutos() {
        return repo.findAllOrderByNomeAsc()
                .stream()
                .map(this::convertEntityToDto1)
                .collect(Collectors.toList());
    }

    public ProdutoDTO convertEntityToDto1(Produto produto) {
        ProdutoDTO produtoDTO = new ProdutoDTO();
        produtoDTO.setId(produto.getId());
        produtoDTO.setPreco(produto.getPreco());
        produtoDTO.setProductType(produto.getProductType());
        produtoDTO.setProductGroup(produto.getProductGroup());
        produtoDTO.setProductCode(produto.getProductCode());
        produtoDTO.setTaxIva(produto.getTaxIva());
        produtoDTO.setFinalPrice(produto.getFinalPrice());
        produtoDTO.setProductDescription(produto.getProductDescription());
        produtoDTO.setUnidadeMedidaId(produto.getUnidadeMedidaId());
        produtoDTO.setImagem(produto.getImagem());
        produtoDTO.setUnidadeMedida(produto.getUnidadeMedida());
        produtoDTO.setStatus(produto.getStatus());
        return produtoDTO;
    }

    public Produto getProdutoById(long id) {
        return repo.findById(id).orElseThrow(() -> new RuntimeException("Produto não encontrado"));
    }

    public ResponseEntity<String> criar(
            String productType,
            long productTypeId,
            String productCode,
            String productGroup,
            long productGroupId,
            String productDescription,
            String unidadeMedida,
            long unidadeMedidaId,
            BigDecimal preco,
            BigDecimal taxIva,
            BigDecimal finalPrice,
            boolean status,
            MultipartFile imagem) {
        Optional<Produto> produtoExistente = repo.findByProductDescription(productDescription);
        if (produtoExistente.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Já existe um produto com a descrição: " + productDescription);
        }

        try {
            if (!imagem.getContentType().equals("image/jpeg") && !imagem.getContentType().equals("image/png")) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Apenas imagens JPEG ou PNG são permitidas.");
            }
            if (imagem.getSize() > 2 * 1024 * 1024) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("A imagem deve ter no máximo 2MB.");
            }

            String uploadDir = "uploads/produtos/";
            String fileName = FileUploadUtil.saveFile(uploadDir, imagem.getOriginalFilename(), imagem);

            Produto produto = new Produto();
            produto.setProductType(productType);
            produto.setProductCode(productCode);
            produto.setProductGroup(productGroup);
            produto.setProductDescription(productDescription);
            produto.setUnidadeMedida(unidadeMedida);
            produto.setPreco(preco);
            produto.setTaxIva(taxIva);
            produto.setFinalPrice(finalPrice);
            produto.setProductGroupId(productGroupId);
            produto.setUnidadeMedidaId(unidadeMedidaId);
            produto.setStatus(status);
            produto.setImagem(fileName);

            if (Objects.nonNull(repo.save(produto))) {
                return ResponseEntity.status(HttpStatus.CREATED)
                        .body("Produto criado com sucesso!");
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Falha ao criar o produto.");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao salvar a imagem: " + e.getMessage());
        }
    }

    public ResponseEntity<String> update(
            long id,
            String productType,
            long productTypeId,
            String productCode,
            String productGroup,
            long productGroupId,
            String productDescription,
            String unidadeMedida,
            long unidadeMedidaId,
            BigDecimal preco,
            BigDecimal taxIva,
            BigDecimal finalPrice,
            boolean status,
            MultipartFile imagem) {
        Optional<Produto> produtoExistente = repo.findById(id);
        if (!produtoExistente.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Produto não encontrado com ID: " + id);
        }

        Produto produto = produtoExistente.get();
        try {
            String fileName = produto.getImagem(); // Manter a imagem existente por padrão
            if (imagem != null && !imagem.isEmpty()) {
                if (!imagem.getContentType().equals("image/jpeg") && !imagem.getContentType().equals("image/png")) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body("Apenas imagens JPEG ou PNG são permitidas.");
                }
                if (imagem.getSize() > 2 * 1024 * 1024) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body("A imagem deve ter no máximo 2MB.");
                }
                String uploadDir = "uploads/produtos/";
                fileName = FileUploadUtil.saveFile(uploadDir, imagem.getOriginalFilename(), imagem);
            }

            produto.setProductType(productType);
            produto.setProductCode(productCode);
            produto.setProductGroup(productGroup);
            produto.setProductDescription(productDescription);
            produto.setUnidadeMedida(unidadeMedida);
            produto.setPreco(preco);
            produto.setTaxIva(taxIva);
            produto.setFinalPrice(finalPrice);
            produto.setProductGroupId(productGroupId);
            produto.setUnidadeMedidaId(unidadeMedidaId);
            produto.setStatus(status);
            produto.setImagem(fileName);

            if (Objects.nonNull(repo.save(produto))) {
                return ResponseEntity.status(HttpStatus.OK)
                        .body("Produto editado com sucesso!");
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Falha ao editar o produto.");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao salvar a imagem: " + e.getMessage());
        }
    }

    public ResponseEntity<String> deleteProduct(long id, boolean status) {
        Optional<Produto> produtoExistente = repo.findById(id);
        if (!produtoExistente.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Produto não encontrado com ID: " + id);
        }
        Produto produtoToUpdate = produtoExistente.get();
        produtoToUpdate.setStatus(status);
        if (Objects.nonNull(repo.save(produtoToUpdate))) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body("Produto deletado com sucesso!");
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Falha ao deletar o produto.");
    }

    public List<Produto> listarProdutosPorGrupo(long grupoId) {
        return repo.findAllProdutosPorGrupoId(grupoId);
    }
}