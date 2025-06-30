package com.dvml.api.service;

import com.dvml.api.dto.ProdutoDTO;
import com.dvml.api.entity.Produto;
import com.dvml.api.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.Objects;
import java.util.Optional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProdutoService {
    @Autowired
    private ProdutoRepository repo;



    public List <ProdutoDTO>listarTodosProdutos() {
        return repo.findAllOrderByNomeAsc()
                .stream()
                .map(this::convertEntityToDto1)
                .collect(Collectors.toList());
    }

    public ProdutoDTO convertEntityToDto1(Produto produto){
        ProdutoDTO produtoDTO = new ProdutoDTO();
        produtoDTO.setId(produto.getId());
        produtoDTO.setPreco(produto.getPreco());
        produtoDTO.setProductType(produto.getProductType());
        produtoDTO.setProductGroup(produto.getProductGroup());
        produtoDTO.setProductCode(produto.getProductCode());
        produtoDTO.setTaxIva(produto.getTaxIva());
        produtoDTO.setProductDescription(produto.getProductDescription());

        return produtoDTO;
    }
    public Produto getProdutoById(long id){
            return repo.findById(id).get();
        }
        public ResponseEntity<String> criar(Produto produto) {
            Optional <Produto> produtoExistente = repo.findByProductDescription(produto.getProductDescription());

            if (produtoExistente.isPresent()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Já existe um produto com a descrição: " + produto.getProductDescription());
            }
            if(Objects.nonNull(repo.save(produto))) {
                return ResponseEntity.status(HttpStatus.CREATED)
                        .body("Produto criado com sucesso!");
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Falha ao criar o produto.");
        }

    public ResponseEntity<String> update(Produto produto){
        Produto ProdutoToUpdate = repo.findById(produto.getId()).get();
        ProdutoToUpdate.setProductCode(produto.getProductCode());
        ProdutoToUpdate.setProductDescription(produto.getProductDescription());
        ProdutoToUpdate.setProductGroup(produto.getProductGroup());
        ProdutoToUpdate.setProductType(produto.getProductType());
        ProdutoToUpdate.setPreco(produto.getPreco());
        ProdutoToUpdate.setTaxIva(produto.getTaxIva());
        ProdutoToUpdate.setStatus(produto.getStatus());
        Optional <Produto> produtoExistente = repo.findByProductDescription(produto.getProductDescription());
        if ((ProdutoToUpdate.getProductDescription() == produto.getProductDescription()) || (produtoExistente.isPresent())) {
            if (Objects.nonNull(repo.save(produto))) {
                return ResponseEntity.status(HttpStatus.CREATED)
                        .body("produto editado com sucesso!");
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Falha ao editar o produto.");
        }

        if (produtoExistente.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Já existe uma produto com o NIF: " + produto.getProductDescription());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("ERRO FATAL: " + produto.getProductDescription());
    }
    public ResponseEntity<String> deleteProduct(Produto produto) {
        Produto ProdutoToUpdate = repo.findById(produto.getId()).get();
        ProdutoToUpdate.setStatus(produto.getStatus());
        if(Objects.nonNull(repo.save(ProdutoToUpdate))) {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("Produto deletado com sucesso!");
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Falha ao deletar o produto.");
    }

    public List<Produto> listarProdutosPorGrupo(long grupoId) {
        return repo.findAllProdutosPorGrupoId(grupoId);
    }


}
