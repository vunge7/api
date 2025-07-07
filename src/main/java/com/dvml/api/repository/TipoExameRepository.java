package com.dvml.api.repository;

import com.dvml.api.entity.TipoExame;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoExameRepository extends JpaRepository<TipoExame, Long> {
    boolean existsByNomeIgnoreCase(String nome);
}