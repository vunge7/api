package com.dvml.api.controller;
import com.dvml.api.dto.UsuarioDTO;
import com.dvml.api.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("usuario/all")
    public ResponseEntity<?> cadastrarUsuario(@RequestBody UsuarioDTO usuarioDTO) {
        return usuarioService.cadastrarUsuario(usuarioDTO);
    }

    @GetMapping("usuario/all")
    public ResponseEntity<List<UsuarioDTO>> listarTodosUsuarios() {
        return ResponseEntity.ok(usuarioService.listarTodosUsuarios());
    }

    @GetMapping("usuario/ativos")
    public ResponseEntity<List<UsuarioDTO>> listarUsuariosAtivos() {
        return ResponseEntity.ok(usuarioService.listarUsuariosAtivos());
    }

    @GetMapping("usuario/inativos")
    public ResponseEntity<List<UsuarioDTO>> listarUsuariosInativos() {
        return ResponseEntity.ok(usuarioService.listarUsuariosInativos());
    }

    @GetMapping("usuario/funcionario/{funcionarioId}")
    public ResponseEntity<List<UsuarioDTO>> listarUsuariosPorFuncionario(@PathVariable Long funcionarioId) {
        return ResponseEntity.ok(usuarioService.listarUsuariosPorFuncionario(funcionarioId));
    }

    @GetMapping("usuario/funcao/{funcaoId}")
    public ResponseEntity<List<UsuarioDTO>> listarUsuariosPorFuncao(@PathVariable Long funcaoId) {
        return ResponseEntity.ok(usuarioService.listarUsuariosPorFuncao(funcaoId));
    }

    @PutMapping("usuario/editar/{id}")
    public ResponseEntity<?> editarUsuario(@PathVariable Long id, @RequestBody UsuarioDTO usuarioDTO) {
        return usuarioService.editarUsuario(id, usuarioDTO);
    }

    @DeleteMapping("usuario/deletar/{id}")
    public ResponseEntity<String> deletarUsuario(@PathVariable Long id) {
        return usuarioService.deleteUsuario(id);
    }

    @PutMapping("usuario/inativar/{id}")
    public ResponseEntity<String> inativarUsuario(@PathVariable Long id) {
        return usuarioService.inativarUsuario(id);
    }

}