package com.dvml.api.repository;

import com.dvml.api.entity.PacienteSeguradora;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PacienteSeguradoraRepository extends JpaRepository<PacienteSeguradora, Long> {
    Optional<PacienteSeguradora> findByPacienteIdAndSeguradoraId(long pacienteId, long seguradoraId);

    @Query(value = "SELECT * FROM paciente_seguradora WHERE paciente_id = :id", nativeQuery = true)
    List<PacienteSeguradora> findSeguradorasByIdPaciente(@Param("id") long id);

}

