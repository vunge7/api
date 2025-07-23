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
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @Autowired
    private FuncaoRepository funcaoRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Transactional
    public ResponseEntity<?> cadastrarUsuario(UsuarioDTO usuarioDTO) {
        try {
            if (usuarioRepository.existsByUserName(usuarioDTO.getUserName())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Erro ao cadastrar usuário: Username já associado a outro usuário");
            }

            Optional<Funcionario> funcionarioOpt = funcionarioRepository.findById(usuarioDTO.getFuncionarioId());
            if (!funcionarioOpt.isPresent()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Erro ao cadastrar usuário: Funcionário não encontrado");
            }

            Optional<Funcao> funcaoOpt = funcaoRepository.findById(usuarioDTO.getFuncaoId());
            if (!funcaoOpt.isPresent()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Erro ao cadastrar usuário: Função não encontrada");
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
            usuario.setDataCadastro(new Date());
            usuario.setDataAtualizacao(new Date());
            usuario.setUsuarioId(0L); // Ajustar conforme necessário
            usuario.setStatus(true);

            validarUsuario(usuario);

            usuario = usuarioRepository.save(usuario);

            UsuarioDTO responseDTO = convertToDTO(usuario);
            return ResponseEntity.ok(responseDTO);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Erro ao cadastrar usuário: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao cadastrar usuário: " + e.getMessage());
        }
    }

    @Transactional
    public ResponseEntity<?> editarUsuario(Long id, UsuarioDTO usuarioDTO) {
        try {
            Optional<Usuario> usuarioOpt = usuarioRepository.findById(id);
            if (!usuarioOpt.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Erro ao editar usuário: Usuário não encontrado com ID: " + id);
            }

            Usuario usuarioExistente = usuarioOpt.get();

            if (!usuarioExistente.getUserName().equals(usuarioDTO.getUserName()) &&
                    usuarioRepository.existsByUserName(usuarioDTO.getUserName())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Erro ao editar usuário: Username já associado a outro usuário");
            }

            Optional<Funcionario> funcionarioOpt = funcionarioRepository.findById(usuarioDTO.getFuncionarioId());
            if (!funcionarioOpt.isPresent()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Erro ao editar usuário: Funcionário não encontrado");
            }

            Optional<Funcao> funcaoOpt = funcaoRepository.findById(usuarioDTO.getFuncaoId());
            if (!funcaoOpt.isPresent()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Erro ao editar usuário: Função não encontrada");
            }

            usuarioExistente.setUserName(usuarioDTO.getUserName());
            usuarioExistente.setSenha(passwordEncoder.encode(usuarioDTO.getSenha()));
            usuarioExistente.setNumeroOrdem(usuarioDTO.getNumeroOrdem());
            usuarioExistente.setEstadoUsuario(usuarioDTO.getEstadoUsuario());
            usuarioExistente.setTipoUsuario(usuarioDTO.getTipoUsuario());
            usuarioExistente.setFuncionarioId(usuarioDTO.getFuncionarioId());
            usuarioExistente.setFuncaoId(usuarioDTO.getFuncaoId());
            usuarioExistente.setIp(usuarioDTO.getIp());
            usuarioExistente.setDataAtualizacao(new Date());

            validarUsuario(usuarioExistente);

            usuarioExistente = usuarioRepository.save(usuarioExistente);

            UsuarioDTO responseDTO = convertToDTO(usuarioExistente);
            return ResponseEntity.ok(responseDTO);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Erro ao editar usuário: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao editar usuário: " + e.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public List<UsuarioDTO> listarTodosUsuarios() {
        return usuarioRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<UsuarioDTO> listarUsuariosAtivos() {
        return usuarioRepository.findByEstadoUsuario(EstadoUsuario.ACTIVO).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<UsuarioDTO> listarUsuariosInativos() {
        return usuarioRepository.findByEstadoUsuario(EstadoUsuario.DESACTIVO).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<UsuarioDTO> listarUsuariosPorFuncionario(Long funcionarioId) {
        return usuarioRepository.findByFuncionarioId(funcionarioId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<UsuarioDTO> listarUsuariosPorFuncao(Long funcaoId) {
        return usuarioRepository.findByFuncaoId(funcaoId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public ResponseEntity<String> deleteUsuario(Long id) {
        if (usuarioRepository.existsById(id)) {
            usuarioRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK)
                    .body("Usuário deletado com sucesso!");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Usuário não encontrado com ID: " + id);
        }
    }

    @Transactional
    public ResponseEntity<String> inativarUsuario(Long id) {
        try {
            Usuario usuario = usuarioRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado com ID: " + id));
            usuario.setEstadoUsuario(EstadoUsuario.DESACTIVO);
            usuarioRepository.save(usuario);
            return ResponseEntity.status(HttpStatus.OK)
                    .body("Usuário inativado com sucesso!");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Erro ao inativar usuário: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao inativar usuário: " + e.getMessage());
        }
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
        return dto;
    }

    private void validarUsuario(Usuario usuario) {
        if (usuario.getUserName() == null || usuario.getUserName().trim().length() < 3 || usuario.getUserName().trim().length() > 100) {
            throw new IllegalArgumentException("Username inválido: deve ter entre 3 e 100 caracteres");
        }
        if (usuario.getSenha() == null || usuario.getSenha().trim().length() < 8 || usuario.getSenha().trim().length() > 100) {
            throw new IllegalArgumentException("Senha inválida: deve ter entre 8 e 100 caracteres");
        }
        if (usuario.getNumeroOrdem() == null || usuario.getNumeroOrdem().trim().length() < 1 || usuario.getNumeroOrdem().trim().length() > 100) {
            throw new IllegalArgumentException("Número de ordem inválido: deve ter entre 1 e 100 caracteres");
        }
        if (usuario.getEstadoUsuario() == null) {
            throw new IllegalArgumentException("Estado do usuário inválido");
        }
        if (usuario.getTipoUsuario() == null) {
            throw new IllegalArgumentException("Tipo de usuário inválido");
        }
        if (usuario.getIp() != null && usuario.getIp().trim().length() > 100) {
            throw new IllegalArgumentException("IP inválido: deve ter no máximo 100 caracteres");
        }
    }

    public Optional<Usuario> findByUserName(String userName) {
        return usuarioRepository.findByUserName(userName);
    }
}