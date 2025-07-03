package com.dvml.api.repository;

import com.dvml.api.entity.LinhaAgenda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Repository
public interface LinhaAgendaRepository extends JpaRepository<LinhaAgenda, Long> {
    @Query(value = "SELECT * FROM linha_agenda WHERE status = true ORDER BY data_realizacao ASC", nativeQuery = true)
    List<LinhaAgenda> findAllActiveOrderByDataRealizacaoAsc();

    List<LinhaAgenda> findByAgendaId(Long agendaId);

    List<LinhaAgenda> findByFuncionarioId(Long funcionarioId);

    @Query("SELECT a FROM LinhaAgenda a WHERE a.dataRealizacao >= :start AND a.dataRealizacao < :end")
    List<LinhaAgenda> findByDataRealizacaoBetween(@Param("start") Date start, @Param("end") Date end);
}