package com.dvml.api.repository;

import com.dvml.api.entity.Inscricao;
import com.dvml.api.util.EstadoInscricao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface InscricaoRepository extends JpaRepository<Inscricao, Long> {


    @Query(value = "SELECT * FROM inscricao i WHERE i.estado = :estado AND i.condicao_inscricao = 'ABERTO'", nativeQuery = true)
    List<Inscricao> getAllInscricaoByEstado1( EstadoInscricao estado );

    @Query(value = "SELECT * FROM inscricao i WHERE i.estado = 'NAO_TRIADO' AND i.condicao_inscricao = 'ABERTO'", nativeQuery = true)
    List<Inscricao> getAllInscricaoNaoTriados();

    @Query(value = "SELECT * FROM inscricao i WHERE i.encaminhamento = 'CONSULTORIO' AND i.condicao_inscricao = 'ABERTO'", nativeQuery = true)
    List<Inscricao> getAllInscricaoEncaminhadoConsulta();
}
