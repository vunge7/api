package com.dvml.api.repository;

import com.dvml.api.entity.LinhaSubsidio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LinhaSubsidioRepository extends JpaRepository<LinhaSubsidio, Long> {
    List<LinhaSubsidio> findByFuncionarioId(Long funcionarioId);
}