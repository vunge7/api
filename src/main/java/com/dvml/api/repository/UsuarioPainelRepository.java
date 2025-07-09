package com.dvml.api.repository;

import com.dvml.api.entity.Painel;
import com.dvml.api.entity.UsuarioPainel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsuarioPainelRepository extends JpaRepository<UsuarioPainel, Long> {

    @Query("SELECT p FROM UsuarioPainel up JOIN Painel p ON up.painelId = p.id WHERE up.usuarioId = :usuarioId")
    List<Painel> findPainelByUsuarioId(@Param("usuarioId") Long usuarioId);
}