package com.dvml.api.repository;

import com.dvml.api.entity.PainelPermissao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PainelPermissaoRepository extends JpaRepository<PainelPermissao, Long> {

    // 🔹 Buscar permissões por usuário
    List<PainelPermissao> findByUsuarioId(Long usuarioId);

    // 🔹 Buscar permissões por usuário e empresa
    List<PainelPermissao> findByUsuarioIdAndEmpresaId(Long usuarioId, Long empresaId);

    // 🔹 Verificar se já existe permissão para um usuário, painel e empresa
    boolean existsByUsuarioIdAndPainelIdAndEmpresaId(Long usuarioId, Long painelId, Long empresaId);

    // 🔹 Buscar empresas distintas com permissão de um usuário (query nativa)
    @Query(value = "SELECT DISTINCT empresa_id FROM painel_permissao WHERE usuario_id = :usuarioId", nativeQuery = true)
    List<Long> findDistinctEmpresaIdsByUsuarioId(@Param("usuarioId") Long usuarioId);
}
