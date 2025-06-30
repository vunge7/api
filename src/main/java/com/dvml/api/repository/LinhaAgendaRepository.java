package com.dvml.api.repository;

import com.dvml.api.entity.LinhaAgenda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LinhaAgendaRepository extends JpaRepository<LinhaAgenda, Long> {
    @Query(value = "SELECT * FROM linha_agenda WHERE status = true ORDER BY data_realizacao ASC", nativeQuery = true)
    List<LinhaAgenda> findAllActiveOrderByDataRealizacaoAsc();

    List<LinhaAgenda> findByAgendaId(Long agendaId);

    List<LinhaAgenda> findByFuncionarioId(Long funcionarioId); // Adicionado para suportar o MedicoService
}