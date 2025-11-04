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

    @Query(value = """
        SELECT p.*
        FROM usuario_painel up
        JOIN painel p ON up.painel_id = p.id
        WHERE up.usuario_id = :usuarioId
    """, nativeQuery = true)
    List<Painel> findPainelByUsuarioId(@Param("usuarioId") Long usuarioId);
}
