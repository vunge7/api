package com.dvml.api.controller;

import com.dvml.api.dto.UsuarioDTO;
import com.dvml.api.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    // 🔹 Criar um novo usuário
    @PostMapping("/add")
    public ResponseEntity<?> cadastrarUsuario(@RequestBody UsuarioDTO usuarioDTO) {
        try {
            UsuarioDTO responseDTO = usuarioService.cadastrarUsuario(usuarioDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Erro ao cadastrar usuário: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro inesperado ao cadastrar usuário: " + e.getMessage());
        }
    }

    // 🔹 Listar todos os usuários
    @GetMapping("/all")
    public ResponseEntity<?> listarTodosUsuarios() {
        List<UsuarioDTO> lista = usuarioService.listarTodosUsuarios();
        if (lista.isEmpty()) {
            return ResponseEntity.ok("Nenhum usuário encontrado.");
        }
        return ResponseEntity.ok(lista);
    }

    // 🔹 Listar usuários ativos
    @GetMapping("/ativos")
    public ResponseEntity<?> listarUsuariosAtivos() {
        List<UsuarioDTO> lista = usuarioService.listarUsuariosAtivos();
        if (lista.isEmpty()) {
            return ResponseEntity.ok("Nenhum usuário ativo encontrado.");
        }
        return ResponseEntity.ok(lista);
    }

    // 🔹 Listar usuários inativos
    @GetMapping("/inativos")
    public ResponseEntity<?> listarUsuariosInativos() {
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
            List<UsuarioDTO> lista = usuarioService.listarUsuariosPorFuncionario(funcionarioId);
            if (lista.isEmpty()) {
                return ResponseEntity.ok("Nenhum usuário encontrado para o funcionário com ID: " + funcionarioId);
            }
            return ResponseEntity.ok(lista);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Erro: " + e.getMessage());
        }
    }

    // 🔹 Listar usuários por função
    @GetMapping("/funcao/{funcaoId}")
    public ResponseEntity<?> listarUsuariosPorFuncao(@PathVariable Long funcaoId) {
        try {
            List<UsuarioDTO> lista = usuarioService.listarUsuariosPorFuncao(funcaoId);
            if (lista.isEmpty()) {
                return ResponseEntity.ok("Nenhum usuário encontrado com a função ID: " + funcaoId);
            }
            return ResponseEntity.ok(lista);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Erro: " + e.getMessage());
        }
    }

    // 🔹 Atualizar usuário
    @PutMapping("/{id}")
    public ResponseEntity<?> editarUsuario(@PathVariable Long id, @RequestBody UsuarioDTO usuarioDTO) {
        return usuarioService.editarUsuario(id, usuarioDTO);
    }

    // 🔹 Deletar usuário
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletarUsuario(@PathVariable Long id) {
        return usuarioService.deleteUsuario(id);
    }

    // 🔹 Inativar usuário
    @PutMapping("/{id}/inativar")
    public ResponseEntity<?> inativarUsuario(@PathVariable Long id) {
        return usuarioService.inativarUsuario(id);
    }
}