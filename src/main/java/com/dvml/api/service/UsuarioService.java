package com.dvml.api.service;

import com.dvml.api.dto.UsuarioDTO;
import com.dvml.api.entity.Funcao;
import com.dvml.api.entity.Funcionario;
import com.dvml.api.entity.Usuario;
import com.dvml.api.repository.FuncaoRepository;
import com.dvml.api.repository.FuncionarioRepository;
import com.dvml.api.repository.UsuarioRepository;
import com.dvml.api.util.EstadoUsuario;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    private static final Logger log = LoggerFactory.getLogger(UsuarioService.class);

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @Autowired
    private FuncaoRepository funcaoRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Transactional
    public UsuarioDTO cadastrarUsuario(UsuarioDTO usuarioDTO) {
        log.info("Iniciando cadastro de usuário: {}", usuarioDTO.getUserName());

        if (usuarioRepository.existsByUserName(usuarioDTO.getUserName())) {
            throw new IllegalArgumentException("Username já associado a outro usuário");
        }

        Optional<Funcionario> funcionarioOpt = funcionarioRepository.findById(usuarioDTO.getFuncionarioId());
        if (!funcionarioOpt.isPresent()) {
            throw new IllegalArgumentException("Funcionário não encontrado com ID: " + usuarioDTO.getFuncionarioId());
        }

        Optional<Funcao> funcaoOpt = funcaoRepository.findById(usuarioDTO.getFuncaoId());
        if (!funcaoOpt.isPresent()) {
            throw new IllegalArgumentException("Função não encontrada com ID: " + usuarioDTO.getFuncaoId());
        }

        Usuario usuario = new Usuario();
        usuario.setUserName(usuarioDTO.getUserName());
        usuario.setSenha(passwordEncoder.encode(usuarioDTO.getSenha()));
        usuario.setNumeroOrdem(usuarioDTO.getNumeroOrdem());
        usuario.setEstadoUsuario(usuarioDTO.getEstadoUsuario());
        usuario.setTipoUsuario(usuarioDTO.getTipoUsuario());
        usuario.setFuncionarioId(usuarioDTO.getFuncionarioId());
        usuario.setFuncaoId(usuarioDTO.getFuncaoId());
        usuario.setIp(usuarioDTO.getIp());
        usuario.setEmpresaId(usuarioDTO.getEmpresaId());
        usuario.setDataCadastro(new Date());
        usuario.setDataAtualizacao(new Date());
        usuario.setStatus(true);

        validarUsuario(usuario);

        usuario = usuarioRepository.save(usuario);
        return convertToDTO(usuario);
    }

    @Transactional
    public ResponseEntity<?> editarUsuario(Long id, UsuarioDTO usuarioDTO) {
        log.info("Atualizando usuário com ID: {}", id);
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(id);
        if (!usuarioOpt.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Usuário não encontrado com ID: " + id);
        }

        Usuario usuarioExistente = usuarioOpt.get();
        usuarioExistente.setUserName(usuarioDTO.getUserName());
        usuarioExistente.setSenha(passwordEncoder.encode(usuarioDTO.getSenha()));
        usuarioExistente.setNumeroOrdem(usuarioDTO.getNumeroOrdem());
        usuarioExistente.setEstadoUsuario(usuarioDTO.getEstadoUsuario());
        usuarioExistente.setTipoUsuario(usuarioDTO.getTipoUsuario());
        usuarioExistente.setFuncionarioId(usuarioDTO.getFuncionarioId());
        usuarioExistente.setFuncaoId(usuarioDTO.getFuncaoId());
        usuarioExistente.setIp(usuarioDTO.getIp());
        usuarioExistente.setEmpresaId(usuarioDTO.getEmpresaId());
        usuarioExistente.setDataAtualizacao(new Date());

        validarUsuario(usuarioExistente);
        usuarioExistente = usuarioRepository.save(usuarioExistente);

        return ResponseEntity.ok(convertToDTO(usuarioExistente));
    }

    @Transactional(readOnly = true)
    public List<UsuarioDTO> listarTodosUsuarios() {
        log.info("Listando todos os usuários");
        return usuarioRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }


    public List<UsuarioDTO> listarUsuariosAtivos() {
        log.info("Listando usuários ativos");
        return usuarioRepository.findByEstadoUsuario(EstadoUsuario.ACTIVO).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }


    public List<UsuarioDTO> listarUsuariosInativos() {
        log.info("Listando usuários inativos");
        return usuarioRepository.findByEstadoUsuario(EstadoUsuario.ACTIVO).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }


    public List<UsuarioDTO> listarUsuariosPorFuncionario(Long funcionarioId) {
        log.info("Listando usuários por funcionário ID: {}", funcionarioId);
        if (!funcionarioRepository.existsById(funcionarioId)) {
            throw new IllegalArgumentException("Funcionário não encontrado com ID: " + funcionarioId);
        }
        return usuarioRepository.findByFuncionarioId(funcionarioId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }


    public List<UsuarioDTO> listarUsuariosPorFuncao(Long funcaoId) {
        log.info("Listando usuários por função ID: {}", funcaoId);
        if (!funcaoRepository.existsById(funcaoId)) {
            throw new IllegalArgumentException("Função não encontrada com ID: " + funcaoId);
        }
        return usuarioRepository.findByFuncaoId(funcaoId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }


    public ResponseEntity<String> deleteUsuario(Long id) {
        log.info("Deletando usuário com ID: {}", id);
        if (!usuarioRepository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Usuário não encontrado com ID: " + id);
        }
        try {
            usuarioRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK)
                    .body("Usuário deletado com sucesso!");
        } catch (DataAccessException e) {
            log.error("Erro ao deletar usuário com ID: {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao deletar usuário: " + e.getMessage());
        }
    }


    public ResponseEntity<String> inativarUsuario(Long id) {
        log.info("Inativando usuário com ID: {}", id);
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(id);
        if (!usuarioOpt.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Usuário não encontrado com ID: " + id);
        }
        Usuario usuario = usuarioOpt.get();
        usuario.setEstadoUsuario(EstadoUsuario.DESACTIVO);
        usuario.setDataAtualizacao(new Date());
        usuarioRepository.save(usuario);
        return ResponseEntity.status(HttpStatus.OK)
                .body("Usuário inativado com sucesso!");
    }


    public Optional<Usuario> findByUserName(String username) {
        log.info("Buscando usuário por username: {}", username);
        return usuarioRepository.findByUserName(username);
    }

    private UsuarioDTO convertToDTO(Usuario usuario) {
        UsuarioDTO dto = new UsuarioDTO();
        dto.setId(usuario.getId());
        dto.setUserName(usuario.getUserName());
        dto.setNumeroOrdem(usuario.getNumeroOrdem());
        dto.setEstadoUsuario(usuario.getEstadoUsuario());
        dto.setTipoUsuario(usuario.getTipoUsuario());
        dto.setFuncionarioId(usuario.getFuncionarioId());
        dto.setFuncaoId(usuario.getFuncaoId());
        dto.setIp(usuario.getIp());
        dto.setEmpresaId(usuario.getEmpresaId());
        return dto;
    }

    private void validarUsuario(Usuario usuario) {
        if (usuario.getUserName() == null || usuario.getUserName().trim().length() < 3)
            throw new IllegalArgumentException("Username inválido");
        if (usuario.getSenha() == null || usuario.getSenha().trim().length() < 8)
            throw new IllegalArgumentException("Senha inválida");
    }
}