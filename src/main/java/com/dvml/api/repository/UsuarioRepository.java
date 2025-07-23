package com.dvml.api.repository;
import com.dvml.api.entity.Usuario;
import com.dvml.api.util.EstadoUsuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    boolean existsByUserName(String userName);
    Optional<Usuario> findByUserName(String userName);
    List<Usuario> findByEstadoUsuario(EstadoUsuario estadoUsuario);
    List<Usuario> findByFuncionarioId(Long funcionarioId);
    List<Usuario> findByFuncaoId(Long funcaoId);
}