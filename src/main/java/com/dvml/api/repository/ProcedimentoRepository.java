package com.dvml.api.repository;

import com.dvml.api.entity.Pessoa;
import com.dvml.api.entity.Procedimento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProcedimentoRepository extends JpaRepository<Procedimento, Long> {
    @Query(value = "SELECT * FROM procedimento", nativeQuery = true)
    List<Procedimento> findAllOrderByNomeAsc();
}
