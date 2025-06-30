package com.dvml.api.repository;

import com.dvml.api.entity.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface EmpresaRepository extends JpaRepository<Empresa, Long> {
    @Query(value = "SELECT * FROM empresa WHERE status = true ORDER BY nome ASC", nativeQuery = true)
    List<Empresa> findAllOrderByNomeAsc();

    Optional<Empresa> findByNif(String Nif);

}