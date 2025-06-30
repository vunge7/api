package com.dvml.api.repository;

import com.dvml.api.entity.Receita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReceitaRepository extends JpaRepository<Receita, Long> {
    @Query(value = "SELECT * FROM receita", nativeQuery = true)
    List<Receita> findAllOrderByNomeAsc();


}
