package com.dvml.api.repository;

import com.dvml.api.entity.LinhasLotes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LinhasLotesRepository extends JpaRepository<LinhasLotes, Long> {
    @Query("SELECT ll FROM LinhasLotes ll WHERE ll.lotes_id = :lotesId")
    List<LinhasLotes> findByLotesId(@Param("lotesId") Long lotesId);

    @Query("SELECT COALESCE(SUM(ll.quantidade), 0) FROM LinhasLotes ll WHERE ll.lotes_id = :lotesId")
    Integer findTotalQuantidadeByLoteId(@Param("lotesId") Long lotesId);
}