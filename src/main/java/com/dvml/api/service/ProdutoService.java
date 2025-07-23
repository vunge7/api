package com.dvml.api.service;

import com.dvml.api.dto.ProdutoArvoreDTO;
import com.dvml.api.dto.ProdutoDTO;
import com.dvml.api.entity.Produto;
import com.dvml.api.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository repo;

    public List<ProdutoDTO> listarTodosProdutos() {
        return repo.findAllOrderByNomeAsc()
                .stream()
                .map(this::convertEntityToDto)
                .collect(Collectors.toList());
    }

    public ProdutoDTO convertEntityToDto(Produto produto) {
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
        produtoDTO.setProdutoPaiId(produto.getProdutoPaiId());
        produtoDTO.setProductGroupId(produto.getProductGroupId());
        return produtoDTO;
    }

    public Produto getProdutoById(long id) {
        return repo.findById(id).orElseThrow(() -> new RuntimeException("Produto não encontrado"));
    }

    public ResponseEntity<String> criar(ProdutoDTO produtoDTO) {
        Optional<Produto> produtoExistente = repo.findByProductDescription(produtoDTO.getProductDescription());
        if (produtoExistente.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Já existe um produto com a descrição: " + produtoDTO.getProductDescription());
        }

        Produto produto = new Produto();
        produto.setProductType(produtoDTO.getProductType());
        produto.setProductCode(produtoDTO.getProductCode());
        produto.setProductGroup(produtoDTO.getProductGroup());
        produto.setProductDescription(produtoDTO.getProductDescription());
        produto.setUnidadeMedida(produtoDTO.getUnidadeMedida());
        produto.setPreco(produtoDTO.getPreco());
        produto.setTaxIva(produtoDTO.getTaxIva());
        produto.setFinalPrice(produtoDTO.getFinalPrice());
        produto.setProductGroupId(produtoDTO.getProductGroupId());
        produto.setUnidadeMedidaId(produtoDTO.getUnidadeMedidaId());
        produto.setStatus(produtoDTO.isStatus());
        produto.setImagem(produtoDTO.getImagem());
        produto.setProdutoPaiId(produtoDTO.getProdutoPaiId());

        if (Objects.nonNull(repo.save(produto))) {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("Produto criado com sucesso!");
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Falha ao criar o produto.");
    }

    public ResponseEntity<String> update(long id, ProdutoDTO produtoDTO) {
        Optional<Produto> produtoExistente = repo.findById(id);
        if (!produtoExistente.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Produto não encontrado com ID: " + id);
        }

        Produto produto = produtoExistente.get();
        produto.setProductType(produtoDTO.getProductType());
        produto.setProductCode(produtoDTO.getProductCode());
        produto.setProductGroup(produtoDTO.getProductGroup());
        produto.setProductDescription(produtoDTO.getProductDescription());
        produto.setUnidadeMedida(produtoDTO.getUnidadeMedida());
        produto.setPreco(produtoDTO.getPreco());
        produto.setTaxIva(produtoDTO.getTaxIva());
        produto.setFinalPrice(produtoDTO.getFinalPrice());
        produto.setProductGroupId(produtoDTO.getProductGroupId());
        produto.setUnidadeMedidaId(produtoDTO.getUnidadeMedidaId());
        produto.setStatus(produtoDTO.isStatus());
        produto.setImagem(produtoDTO.getImagem());
        produto.setProdutoPaiId(produtoDTO.getProdutoPaiId());

        if (Objects.nonNull(repo.save(produto))) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body("Produto editado com sucesso!");
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Falha ao editar o produto.");
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

    // MONTA A ÁRVORE DE PRODUTOS RECURSIVAMENTE
    public ProdutoArvoreDTO montarArvoreProduto(Long produtoId) {
        Produto produto = repo.findById(produtoId)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        ProdutoArvoreDTO dto = new ProdutoArvoreDTO();
        dto.setId(produto.getId());
        dto.setProductCode(produto.getProductCode());
        dto.setProductDescription(produto.getProductDescription());

        List<Produto> filhos = repo.findByProdutoPaiId(produtoId);
        List<ProdutoArvoreDTO> filhosDto = filhos.stream()
                .map(filho -> montarArvoreProduto(filho.getId()))
                .collect(Collectors.toList());

        dto.setFilhos(filhosDto);
        return dto;
    }
}
