package com.dvml.api.controller;

import com.dvml.api.dto.UsuarioDTO;
import com.dvml.api.service.UsuarioService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    private static final Logger logger = LoggerFactory.getLogger(UsuarioController.class);

    @Autowired
    private UsuarioService usuarioService;

    // 🔹 Criar um novo usuário
    @PostMapping("/add")
    public ResponseEntity<?> cadastrarUsuario(@RequestBody UsuarioDTO usuarioDTO) {
        try {
            UsuarioDTO responseDTO = usuarioService.cadastrarUsuario(usuarioDTO);
            logger.info("Usuário criado com sucesso: {}", responseDTO.getUserName());
            return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
        } catch (IllegalArgumentException e) {
            logger.error("Erro ao cadastrar usuário: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Erro ao cadastrar usuário: " + e.getMessage());
        } catch (Exception e) {
            logger.error("Erro inesperado ao cadastrar usuário: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro inesperado ao cadastrar usuário: " + e.getMessage());
        }
    }

    // 🔹 Listar todos os usuários
    @GetMapping("/all")
    public ResponseEntity<?> listarTodosUsuarios() {
        logger.info("Requisição para listar todos os usuários");
        List<UsuarioDTO> lista = usuarioService.listarTodosUsuarios();
        if (lista.isEmpty()) {
            return ResponseEntity.ok("Nenhum usuário encontrado.");
        }
        return ResponseEntity.ok(lista);
    }

    // 🔹 Listar usuários ativos
    @GetMapping("/ativos")
    public ResponseEntity<?> listarUsuariosAtivos() {
        logger.info("Requisição para listar usuários ativos");
        List<UsuarioDTO> lista = usuarioService.listarUsuariosAtivos();
        if (lista.isEmpty()) {
            return ResponseEntity.ok("Nenhum usuário ativo encontrado.");
        }
        return ResponseEntity.ok(lista);
    }

    // 🔹 Listar usuários inativos
    @GetMapping("/inativos")
    public ResponseEntity<?> listarUsuariosInativos() {
        logger.info("Requisição para listar usuários inativos");
        List<UsuarioDTO> lista = usuarioService.listarUsuariosInativos();
        if (lista.isEmpty()) {
            return ResponseEntity.ok("Nenhum usuário inativo encontrado.");
        }
        return ResponseEntity.ok(lista);
    }

    // 🔹 Listar usuários por funcionário
    @GetMapping("/funcionario/{funcionarioId}")
    public ResponseEntity<?> listarUsuariosPorFuncionario(@PathVariable Long funcionarioId) {
        try {
            logger.info("Listando usuários do funcionário ID: {}", funcionarioId);
            List<UsuarioDTO> lista = usuarioService.listarUsuariosPorFuncionario(funcionarioId);
            if (lista.isEmpty()) {
                return ResponseEntity.ok("Nenhum usuário encontrado para o funcionário com ID: " + funcionarioId);
            }
            return ResponseEntity.ok(lista);
        } catch (IllegalArgumentException e) {
            logger.error("Erro ao listar usuários por funcionário: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Erro: " + e.getMessage());
        }
    }

    // 🔹 Listar usuários por função
    @GetMapping("/funcao/{funcaoId}")
    public ResponseEntity<?> listarUsuariosPorFuncao(@PathVariable Long funcaoId) {
        try {
            logger.info("Listando usuários da função ID: {}", funcaoId);
            List<UsuarioDTO> lista = usuarioService.listarUsuariosPorFuncao(funcaoId);
            if (lista.isEmpty()) {
                return ResponseEntity.ok("Nenhum usuário encontrado com a função ID: " + funcaoId);
            }
            return ResponseEntity.ok(lista);
        } catch (IllegalArgumentException e) {
            logger.error("Erro ao listar usuários por função: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Erro: " + e.getMessage());
        }
    }

    // 🔹 Atualizar usuário
    @PutMapping("/{id}")
    public ResponseEntity<?> editarUsuario(@PathVariable Long id, @RequestBody UsuarioDTO usuarioDTO) {
        logger.info("Atualizando usuário ID: {}", id);
        return usuarioService.editarUsuario(id, usuarioDTO);
    }

    // 🔹 Deletar usuário
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletarUsuario(@PathVariable Long id) {
        logger.info("Deletando usuário ID: {}", id);
        return usuarioService.deleteUsuario(id);
    }

    // 🔹 Inativar usuário
    @PutMapping("/{id}/inativar")
    public ResponseEntity<?> inativarUsuario(@PathVariable Long id) {
        logger.info("Inativando usuário ID: {}", id);
        return usuarioService.inativarUsuario(id);
    }

    // 🔹 Listar usuários por filial (empresa)
    @GetMapping("/filial/{empresaId}")
    public ResponseEntity<?> listarUsuariosPorFilial(@PathVariable Long empresaId) {
        try {
            logger.info("Requisição para listar usuários da filial (empresaId): {}", empresaId);
            List<UsuarioDTO> usuarios = usuarioService.listarUsuariosPorFilial(empresaId);

            if (usuarios.isEmpty()) {
                logger.warn("Nenhum usuário encontrado para a filial ID: {}", empresaId);
                return ResponseEntity.ok("Nenhum usuário encontrado para a filial informada.");
            }

            return ResponseEntity.ok(usuarios);

        } catch (IllegalArgumentException e) {
            logger.error("Erro ao listar usuários por filial: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Erro: " + e.getMessage());
        } catch (Exception e) {
            logger.error("Erro inesperado ao listar usuários por filial: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro inesperado: " + e.getMessage());
        }
    }

    // 🔹 Listar todas as filiais associadas a um usuário
    @GetMapping("/filiais/{usuarioId}")
    public ResponseEntity<?> listarFiliaisPorUsuario(@PathVariable Long usuarioId) {
        logger.info("Requisição para listar filiais do usuário ID: {}", usuarioId);
        try {
            Set<Long> filiais = usuarioService.listarFiliaisPorUsuario(usuarioId);
            if (filiais.isEmpty()) {
                return ResponseEntity.ok("Nenhuma filial associada a este usuário.");
            }
            return ResponseEntity.ok(filiais);
        } catch (IllegalArgumentException e) {
            logger.error("Erro ao listar filiais por usuário: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Erro: " + e.getMessage());
        } catch (Exception e) {
            logger.error("Erro inesperado: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro inesperado: " + e.getMessage());
        }
    }
}
