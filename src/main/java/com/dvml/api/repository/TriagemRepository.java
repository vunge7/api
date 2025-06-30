package com.dvml.api.repository;

import com.dvml.api.entity.Triagem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TriagemRepository extends JpaRepository<Triagem, Long> {
    @Query(value = "SELECT max(id) FROM triagem", nativeQuery = true)
    long  findLastId();

}
