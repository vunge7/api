package com.dvml.api.controller;

import com.dvml.api.dto.UsuarioDTO;
import com.dvml.api.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

<<<<<<< HEAD
    @PostMapping("/usuario/all")
    public ResponseEntity<?> cadastrarUsuario(@RequestBody UsuarioDTO usuarioDTO) {
        try {
            UsuarioDTO responseDTO = usuarioService.cadastrarUsuario(usuarioDTO);
            return ResponseEntity.ok(responseDTO);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Erro ao cadastrar usuário: " + e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao cadastrar usuário: " + e.getMessage());
        }
    }
    @GetMapping("usuario/all")
    public ResponseEntity<List<UsuarioDTO>> listarTodosUsuarios() {
        return ResponseEntity.ok(usuarioService.listarTodosUsuarios());
=======

    @PostMapping("/add")
    public ResponseEntity<?> cadastrarUsuario(@RequestBody UsuarioDTO usuarioDTO) {
        usuarioService.cadastrarUsuario(usuarioDTO);
        return ResponseEntity.ok("Usuário cadastrado com sucesso!");
    }

    @GetMapping("/all")
    public ResponseEntity<?> listarTodosUsuarios() {
        List<UsuarioDTO> lista = usuarioService.listarTodosUsuarios();
        if (lista.isEmpty()) {
            return ResponseEntity.ok("Nenhum usuário encontrado.");
        }
        return ResponseEntity.ok(lista);
>>>>>>> 6314628d6e4b5382e1c7b18b10a62308ea82a8ca
    }

    @GetMapping("/ativos")
    public ResponseEntity<?> listarUsuariosAtivos() {
        List<UsuarioDTO> lista = usuarioService.listarUsuariosAtivos();
        if (lista.isEmpty()) {
            return ResponseEntity.ok("Nenhum usuário ativo encontrado.");
        }
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/inativos")
    public ResponseEntity<?> listarUsuariosInativos() {
        List<UsuarioDTO> lista = usuarioService.listarUsuariosInativos();
        if (lista.isEmpty()) {
            return ResponseEntity.ok("Nenhum usuário inativo encontrado.");
        }
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/funcionario/{funcionarioId}")
    public ResponseEntity<?> listarUsuariosPorFuncionario(@PathVariable Long funcionarioId) {
        List<UsuarioDTO> lista = usuarioService.listarUsuariosPorFuncionario(funcionarioId);
        if (lista.isEmpty()) {
            return ResponseEntity.ok("Nenhum usuário encontrado para o funcionário com ID: " + funcionarioId);
        }
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/funcao/{funcaoId}")
    public ResponseEntity<?> listarUsuariosPorFuncao(@PathVariable Long funcaoId) {
        List<UsuarioDTO> lista = usuarioService.listarUsuariosPorFuncao(funcaoId);
        if (lista.isEmpty()) {
            return ResponseEntity.ok("Nenhum usuário encontrado com a função ID: " + funcaoId);
        }
        return ResponseEntity.ok(lista);
    }

    @PutMapping("/editar/{id}")
    public ResponseEntity<?> editarUsuario(@PathVariable Long id, @RequestBody UsuarioDTO usuarioDTO) {
        usuarioService.editarUsuario(id, usuarioDTO);
        return ResponseEntity.ok("Usuário com ID " + id + " atualizado com sucesso.");
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<?> deletarUsuario(@PathVariable Long id) {
        usuarioService.deleteUsuario(id);
        return ResponseEntity.ok("Usuário com ID " + id + " deletado com sucesso.");
    }

    @PutMapping("/inativar/{id}")
    public ResponseEntity<?> inativarUsuario(@PathVariable Long id) {
        usuarioService.inativarUsuario(id);
        return ResponseEntity.ok("Usuário com ID " + id + " foi inativado com sucesso.");
    }
}
