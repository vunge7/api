package com.dvml.api.repository;

import com.dvml.api.entity.LinhaResultado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LinhaResultadoRepository extends JpaRepository<LinhaResultado, Long> {
}
