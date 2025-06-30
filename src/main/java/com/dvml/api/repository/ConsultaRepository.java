package com.dvml.api.repository;

import com.dvml.api.entity.Consulta;
import com.dvml.api.entity.Inscricao;
import com.dvml.api.util.EstadoInscricao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ConsultaRepository extends JpaRepository<Consulta, Long> {

    @Query(value = "SELECT * FROM consulta  WHERE estado_consulta = :estado AND inscricao_id = :idInscricao", nativeQuery = true)
    Consulta getConsultaByEstadoInscricaoAndIdIscricao(String estado, long idInscricao);

}
