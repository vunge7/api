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

    @Query("SELECT COALESCE(SUM(l.qtdActual), 0) FROM LinhaOperacaoStock l " +
            "WHERE l.armazemIdOrigem = :armazemId AND l.loteIdOrigem = :loteId AND l.produtoId = :produtoId")
    Optional<BigDecimal> sumQtdActualByArmazemLoteAndProduto(
            @Param("armazemId") Long armazemId,
            @Param("loteId") Long loteId,
            @Param("produtoId") Long produtoId);

    @Query("SELECT COALESCE(SUM(l.qtdActual), 0) FROM LinhaOperacaoStock l WHERE l.loteIdOrigem = :loteId")
    BigDecimal sumQtdActualByLoteId(@Param("loteId") Long loteId);
}