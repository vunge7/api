package com.dvml.api.repository;

import com.dvml.api.entity.UnidadeMedidas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UnidadeMedidaRepository extends JpaRepository<UnidadeMedidas, Long> {
}
