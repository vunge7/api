package com.dvml.api.repository;
import com.dvml.api.entity.Agenda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AgendaRepository extends JpaRepository<Agenda, Long> {
    @Query(value = "SELECT * FROM agenda WHERE status = true ORDER BY descricao ASC", nativeQuery = true)
    List<Agenda> findAllOrderByNomeAsc();
}