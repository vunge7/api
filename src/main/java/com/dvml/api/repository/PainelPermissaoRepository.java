package com.dvml.api.repository;

import com.dvml.api.entity.PainelPermissao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PainelPermissaoRepository extends JpaRepository<PainelPermissao, Long> {

    // Buscar permissões por usuário
    List<PainelPermissao> findByUsuarioId(Long usuarioId);

    // Buscar permissões por usuário e empresa
    List<PainelPermissao> findByUsuarioIdAndEmpresaId(Long usuarioId, Long empresaId);

    // Verifica se já existe permissão para usuário, painel e empresa
    boolean existsByUsuarioIdAndPainelIdAndEmpresaId(Long usuarioId, Long painelId, Long empresaId);
}
