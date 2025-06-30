package com.dvml.api.repository;

import com.dvml.api.entity.ProductGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProductGroupRepository extends JpaRepository<ProductGroup, Long> {
    @Query(value = "SELECT * FROM product_group ORDER BY designacao_produto ASC", nativeQuery = true)
    List<ProductGroup> findAllOrderByNomeAsc();

    Optional<ProductGroup> findByDesignacaoProdutoIgnoreCase(String designacaoProduto);

    boolean existsByDesignacaoProdutoIgnoreCase(String designacaoProduto);

    boolean existsByDesignacaoProdutoIgnoreCaseAndIdNot(String designacaoProduto, Long id);
}