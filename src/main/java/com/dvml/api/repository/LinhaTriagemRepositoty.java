package com.dvml.api.repository;

import com.dvml.api.entity.LinhaTriagem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LinhaTriagemRepositoty extends JpaRepository<LinhaTriagem, Long> {

    // 🔹 Buscar sinais vitais por paciente e campo (nativo)
    @Query(value = """
        SELECT t.data_criacao, lt.valor, lt.campo
        FROM linha_triagem lt
        JOIN triagem t ON t.id = lt.triagem_id
        JOIN inscricao i ON i.id = t.inscricao_id
        JOIN paciente p ON p.id = i.paciente_id
        WHERE p.id = :pacienteId
        AND lt.campo = :campo
    """, nativeQuery = true)
    List<Object[]> buscarSinalVitalPorPaciente(
            @Param("pacienteId") Long pacienteId,
            @Param("campo") String campo
    );

    // 🔹 Buscar todos os sinais vitais de um paciente (nativo)
    @Query(value = """
        SELECT t.data_criacao, lt.valor, lt.campo
        FROM linha_triagem lt
        JOIN triagem t ON t.id = lt.triagem_id
        JOIN inscricao i ON i.id = t.inscricao_id
        JOIN paciente p ON p.id = i.paciente_id
    """, nativeQuery = true)
    List<Object[]> buscarTodosSinaisComCampo(@Param("pacienteId") Long pacienteId);
}
