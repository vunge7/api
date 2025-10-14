package com.dvml.api.repository;

import com.dvml.api.entity.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EmpresaRepository extends JpaRepository<Empresa, Long> {

    // Lista todas as empresas ativas, ordenadas por nome
    @Query(value = "SELECT * FROM empresa WHERE status = true ORDER BY nome ASC", nativeQuery = true)
    List<Empresa> findAllOrderByNomeAsc();

    // Busca empresas pelo NIF (retorna lista para evitar erro de múltiplos resultados)
    List<Empresa> findByNif(String nif);

}
