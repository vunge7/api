package com.dvml.api.repository;

import com.dvml.api.entity.LinhaOperacaoStock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface LinhaOperacaoStockRepository extends JpaRepository<LinhaOperacaoStock, Long> {

    List<LinhaOperacaoStock> findByLoteIdOrigem(Long loteId);

    @Query(value = "SELECT COALESCE(SUM(qtd_actual), 0) " +
            "FROM linha_operacao_stock " +
            "WHERE armazem_id_origem = :armazemId " +
            "AND lote_id_origem = :loteId " +
            "AND produto_id = :produtoId",
            nativeQuery = true)
    Optional<BigDecimal> sumQtdActualByArmazemLoteAndProduto(
            @Param("armazemId") Long armazemId,
            @Param("loteId") Long loteId,
            @Param("produtoId") Long produtoId);

    @Query(value = "SELECT COALESCE(SUM(qtd_actual), 0) " +
            "FROM linha_operacao_stock " +
            "WHERE lote_id_origem = :loteId",
            nativeQuery = true)
    BigDecimal sumQtdActualByLoteId(@Param("loteId") Long loteId);
}
