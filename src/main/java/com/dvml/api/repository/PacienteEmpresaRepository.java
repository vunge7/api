package com.dvml.api.repository;

import com.dvml.api.entity.PacienteEmpresa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PacienteEmpresaRepository extends JpaRepository<PacienteEmpresa, Long> {
}