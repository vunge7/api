package com.dvml.api.repository;

import com.dvml.api.entity.OperacaoStock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OperacaoStockRepository extends JpaRepository<OperacaoStock, Long> {
}