package com.dvml.api.repository;

import com.dvml.api.entity.Usuario;
import com.dvml.api.util.EstadoUsuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    boolean existsByUserName(String userName);
    Optional<Usuario> findByUserName(String userName);
    List<Usuario> findByEstadoUsuario(EstadoUsuario estadoUsuario);
    List<Usuario> findByFuncionarioId(Long funcionarioId);
    List<Usuario> findByFuncaoId(Long funcaoId);

    // 🔹 Buscar usuários por filial (empresaId)
    List<Usuario> findByEmpresaId(Long empresaId);

    // 🔹 Buscar empresas associadas a um usuário
    @Query(value = """
        SELECT DISTINCT u.empresa_id
        FROM usuario u
        WHERE u.id = :usuarioId
    """, nativeQuery = true)
    Set<Long> findEmpresasByUsuarioId(@Param("usuarioId") Long usuarioId);

    // 🔹 Buscar todos os usuários de uma filial (empresa) específica
    @Query(value = """
        SELECT *
        FROM usuario
        WHERE empresa_id = :empresaId
    """, nativeQuery = true)
    List<Usuario> listarUsuariosPorFilial(@Param("empresaId") Long empresaId);
}
