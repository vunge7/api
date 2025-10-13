package com.dvml.api.repository;

import com.dvml.api.entity.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PacienteRepository extends JpaRepository<Paciente, Long> {

    // 🔍 Busca um paciente pelo ID da pessoa associada
    Paciente findByPessoaId(Long pessoaId);
}
