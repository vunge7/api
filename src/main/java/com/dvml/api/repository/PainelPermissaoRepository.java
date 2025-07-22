package com.dvml.api.repository;

import com.dvml.api.entity.PainelPermissao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PainelPermissaoRepository extends JpaRepository<PainelPermissao, Long> {
    List<PainelPermissao> findByUsuarioId(Long usuarioId);
    List<PainelPermissao> findByUsuarioIdAndFilialId(Long usuarioId, Long filialId);
}
