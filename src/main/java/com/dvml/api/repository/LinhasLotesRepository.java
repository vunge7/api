package com.dvml.api.repository;

import com.dvml.api.entity.LinhasLotes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LinhasLotesRepository extends JpaRepository<LinhasLotes, Long> {

    @Query(value = "SELECT * FROM linhas_lotes WHERE lotes_id = :lotesId", nativeQuery = true)
    List<LinhasLotes> findByLotesId(@Param("lotesId") Long lotesId);

    @Query(value = "SELECT COALESCE(SUM(quantidade), 0) FROM linhas_lotes WHERE lotes_id = :lotesId", nativeQuery = true)
    Integer findTotalQuantidadeByLoteId(@Param("lotesId") Long lotesId);
}
