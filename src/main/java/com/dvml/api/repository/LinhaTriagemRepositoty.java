package com.dvml.api.repository;

import com.dvml.api.dto.SinalVitalDTO;
import com.dvml.api.entity.LinhaTriagem;
import com.dvml.api.util.Campo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LinhaTriagemRepositoty extends JpaRepository<LinhaTriagem, Long> {

    @Query("""
        SELECT new com.dvml.api.dto.SinalVitalDTO(t.dataCriacao, lt.valor)
        FROM LinhaTriagem lt
        JOIN Triagem t ON t.id = lt.triagemId
        JOIN Inscricao i ON i.id = t.inscricaoId
        JOIN Paciente p ON p.id = i.pacienteId
        WHERE p.id = :pacienteId
        AND lt.campo = :campo
    """)
    List<SinalVitalDTO> buscarSinalVitalPorPaciente(
            @Param("pacienteId") Long pacienteId,
            @Param("campo") Campo campo
    );

    @Query(value = """
        SELECT t.data_criacao, lt.valor
        FROM linha_triagem lt
        JOIN triagem t ON t.id = lt.triagem_id
        JOIN inscricao i ON i.id = t.inscricao_id
        JOIN paciente p ON p.id = i.paciente_id
        WHERE p.id = :pacienteId
    """, nativeQuery = true)
    List<Object[]> buscarTodosSinaisPorPacienteNativo(@Param("pacienteId") Long pacienteId);
}
