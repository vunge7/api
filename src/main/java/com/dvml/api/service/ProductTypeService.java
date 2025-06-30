package com.dvml.api.service;


import com.dvml.api.entity.ProductType;
import com.dvml.api.repository.ProductTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ProductTypeService {
    @Autowired
    private ProductTypeRepository repo;

    public List<ProductType> listarTodosTipos() {
        return repo.findAllOrderByNomeAsc();
    }

    public ProductType getProdutoById(long id){
        return repo.findById(id).get();
    }

    public ProductType update(ProductType tipo){
        ProductType ProdutoToUpdate = repo.findById(tipo.getId()).get();
        ProdutoToUpdate.setDesignacaoTipoProduto(tipo.getDesignacaoTipoProduto());
        return repo.save(ProdutoToUpdate);
    }
    public void deleteType(long id) {
        if (repo.existsById(id)) {
            repo.deleteById(id);
        } else {
            throw new IllegalArgumentException("Tipo não encontrado com o ID: " + id);
        }

    }

    public ResponseEntity<String> criar(ProductType tipo) {
        Optional <ProductType> produtoExistente = repo.findByDesignacaoTipoProduto(tipo.getDesignacaoTipoProduto());

        if (produtoExistente.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Já existe um Tipo com a designacao: " + tipo.getDesignacaoTipoProduto());
        }
        if(Objects.nonNull(repo.save(tipo))) {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("Tipo criado com sucesso!");
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Falha ao criar o Tipo.");
    }

}


