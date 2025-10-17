package com.dvml.api.repository;

import com.dvml.api.entity.Consulta;
import com.dvml.api.util.EstadoConsulta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ConsultaRepository extends JpaRepository<Consulta, Long> {

    @Query(value = "SELECT * FROM consulta WHERE inscricao_id = :inscricaoId AND estado_consulta = :estadoConsulta LIMIT 1", nativeQuery = true)
    Consulta findByInscricaoIdAndEstadoConsulta(@Param("inscricaoId") Long inscricaoId, @Param("estadoConsulta") String estadoConsulta);
}
