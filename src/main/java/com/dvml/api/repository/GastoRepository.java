package com.dvml.api.repository;

import com.dvml.api.entity.Gasto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface GastoRepository extends JpaRepository<Gasto, Long> {
    @Query(value = "SELECT * FROM Gasto", nativeQuery = true)
    List<Gasto> findAllOrderByNomeAsc();
    Optional<Gasto> findByInscricaoId(long inscricaoId);

}