package com.dvml.api.repository;

import com.dvml.api.entity.Fornecedor;
import com.dvml.api.util.EstadoFornecedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FornecedorRepository extends JpaRepository<Fornecedor, Long> {
    boolean existsByNif(String nif);
    List<Fornecedor> findByEstadoFornecedor(EstadoFornecedor estado);
}