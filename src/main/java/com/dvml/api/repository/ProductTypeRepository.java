package com.dvml.api.repository;

import com.dvml.api.entity.ProductType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.List;

public interface ProductTypeRepository extends JpaRepository<ProductType, Long> {
    @Query(value = "SELECT * FROM product_type ORDER BY designacao_tipo ASC", nativeQuery = true)
    List<ProductType> findAllOrderByNomeAsc();

    Optional<ProductType> findByDesignacaoTipoProduto(String designacaoTipo);
}
