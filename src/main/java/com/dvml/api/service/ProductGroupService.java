package com.dvml.api.service;

import com.dvml.api.entity.ProductGroup;
import com.dvml.api.repository.ProductGroupRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductGroupService {

    private static final Logger logger = LoggerFactory.getLogger(ProductGroupService.class);

    @Autowired
    private ProductGroupRepository repository;

    public List<ProductGroup> listarTodosGrupos() {
        logger.info("Listando todos os grupos de produtos");
        return repository.findAllOrderByNomeAsc();
    }

    public ProductGroup getProdutoById(long id) {
        logger.info("Buscando grupo de produto por ID: {}", id);
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Grupo de produto não encontrado: ID " + id));
    }

    public Optional<ProductGroup> findByDesignacaoProduto(String designacaoProduto) {
        logger.info("Buscando grupo de produto por designação: {}", designacaoProduto);
        return repository.findByDesignacaoProdutoIgnoreCase(designacaoProduto.trim());
    }

    public ResponseEntity<String> criar(ProductGroup group) {
        logger.info("Criando grupo de produto: {}", group.getDesignacaoProduto());
        try {
            if (repository.existsByDesignacaoProdutoIgnoreCase(group.getDesignacaoProduto().trim())) {
                logger.warn("Designação já existe: {}", group.getDesignacaoProduto());
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Erro: Designação já existe: " + group.getDesignacaoProduto());
            }
            repository.save(group);
            logger.info("Grupo de produto criado com sucesso: ID {}", group.getId());
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("Grupo de produto criado com sucesso: " + group.getDesignacaoProduto());
        } catch (Exception e) {
            logger.error("Erro ao criar grupo de produto: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao criar grupo de produto: " + e.getMessage());
        }
    }

    public void update(ProductGroup group) {
        logger.info("Atualizando grupo de produto: ID {}", group.getId());
        if (!repository.existsById(group.getId())) {
            logger.warn("Grupo de produto não encontrado: ID {}", group.getId());
            throw new RuntimeException("Grupo de produto não encontrado: ID " + group.getId());
        }
        if (repository.existsByDesignacaoProdutoIgnoreCaseAndIdNot(group.getDesignacaoProduto().trim(), group.getId())) {
            logger.warn("Designação já existe: {}", group.getDesignacaoProduto());
            throw new IllegalArgumentException("Designação já existe: " + group.getDesignacaoProduto());
        }
        repository.save(group);
        logger.info("Grupo de produto atualizado com sucesso: ID {}", group.getId());
    }

    public void deleteGroup(long id) {
        logger.info("Deletando grupo de produto: ID {}", id);
        if (!repository.existsById(id)) {
            logger.warn("Grupo de produto não encontrado: ID {}", id);
            throw new RuntimeException("Grupo de produto não encontrado: ID " + id);
        }
        repository.deleteById(id);
        logger.info("Grupo de produto deletado com sucesso: ID {}", id);
    }
}