package com.dvml.api.repository;

import com.dvml.api.entity.Exame;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExameRepository extends JpaRepository<Exame, Long> {
    List<Exame> findByPacienteId(Long pacienteId);
    List<Exame> findByRequisicaoExameId(Long requisicaoExameId);
}