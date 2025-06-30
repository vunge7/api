package com.dvml.api.repository;

import com.dvml.api.entity.LinhaReceita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface LinhaReceitaRepository extends JpaRepository<LinhaReceita, Long> {
    @Query(value = "SELECT * FROM linha_receita", nativeQuery = true)
    List<LinhaReceita> findAllOrderByNomeAsc();


}
