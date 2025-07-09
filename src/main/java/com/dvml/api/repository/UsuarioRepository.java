package com.dvml.api.repository;

import com.dvml.api.entity.Usuario;
import com.dvml.api.util.EstadoUsuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    // Verifica se existe um usuário com o username fornecido
    boolean existsByUserName(String userName);

    // Busca usuários por estado (ATIVO ou INATIVO)
    List<Usuario> findByEstadoUsuario(EstadoUsuario estadoUsuario);

    // Busca usuários por funcionarioId
    List<Usuario> findByFuncionarioId(Long funcionarioId);

    // Busca usuários por funcao knocked
    List<Usuario> findByFuncaoId(Long funcaoId);
}