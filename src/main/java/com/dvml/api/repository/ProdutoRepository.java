package com.dvml.api.repository;

import com.dvml.api.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    @Query(value = "SELECT * FROM produto WHERE status = true ORDER BY product_description ASC", nativeQuery = true)
    List<Produto> findAllOrderByNomeAsc();

    @Query(value = "SELECT * FROM produto WHERE product_group_id = :grupoId", nativeQuery = true)
    List<Produto> findAllProdutosPorGrupoId(@Param("grupoId") long grupoId);

    Optional<Produto> findByProductDescription(String productDescription);
}