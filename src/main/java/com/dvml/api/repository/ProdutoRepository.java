package com.dvml.api.repository;

import com.dvml.api.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    // Buscar todos produtos com status true, ordenados por descrição
    @Query(value = "SELECT * FROM produto WHERE status = true ORDER BY product_description ASC", nativeQuery = true)
    List<Produto> findAllOrderByNomeAsc();

    // Buscar todos produtos de um grupo específico
    @Query(value = "SELECT * FROM produto WHERE product_group_id = :grupoId", nativeQuery = true)
    List<Produto> findAllProdutosPorGrupoId(@Param("grupoId") long grupoId);

    // Buscar todos produtos filhos de um produto pai específico
    List<Produto> findByProdutoPaiId(Long produtoPaiId);

    // Buscar produto pelo nome (descrição)
    Optional<Produto> findByProductDescription(String productDescription);
}
