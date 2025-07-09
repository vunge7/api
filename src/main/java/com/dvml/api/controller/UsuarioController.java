package com.dvml.api.controller;

import com.dvml.api.dto.UsuarioDTO;
import com.dvml.api.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("usuario/add")
    public ResponseEntity<?> cadastrarUsuario(@Valid @RequestBody UsuarioDTO usuarioDTO) {
        return usuarioService.cadastrarUsuario(usuarioDTO);
    }

    @GetMapping("usuario/all")
    public ResponseEntity<List<UsuarioDTO>> listarTodosUsuarios() {
        List<UsuarioDTO> usuarios = usuarioService.listarTodosUsuarios();
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("usuario/ativos")
    public ResponseEntity<List<UsuarioDTO>> listarUsuariosAtivos() {
        List<UsuarioDTO> usuarios = usuarioService.listarUsuariosAtivos();
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("usuario/inativos")
    public ResponseEntity<List<UsuarioDTO>> listarUsuariosInativos() {
        List<UsuarioDTO> usuarios = usuarioService.listarUsuariosInativos();
        return ResponseEntity.ok(usuarios);
    }

    @PutMapping("usuario/{id}")
    public ResponseEntity<?> editarUsuario(@PathVariable Long id, @Valid @RequestBody UsuarioDTO usuarioDTO) {
        // Validação básica do ID
        if (id == null || id <= 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("ID inválido: deve ser um número positivo.");
        }

        // Chama o serviço para editar o usuário
        ResponseEntity<?> serviceResponse = usuarioService.editarUsuario(id, usuarioDTO);

        // Personaliza a resposta de sucesso
        if (serviceResponse.getStatusCode() == HttpStatus.OK) {
            Map<String, Object> response = new HashMap<>();
            response.put("mensagem", "Usuário com ID " + id + " editado com sucesso!");
            response.put("usuario", serviceResponse.getBody());
            return ResponseEntity.ok(response);
        }

        // Propaga erros do serviço
        return serviceResponse;
    }

    @DeleteMapping("usuario/{id}")
    public ResponseEntity<String> deletarUsuario(@PathVariable Long id) {
        return usuarioService.deleteUsuario(id);
    }

    @PutMapping("usuario/inativar/{id}")
    public ResponseEntity<String> inativarUsuario(@PathVariable Long id) {
        return usuarioService.inativarUsuario(id);
    }

    @GetMapping("usuario/{userName}")
    public ResponseEntity<Boolean> verificarUserName(@PathVariable String userName) {
        boolean exists = usuarioService.existsByUserName(userName);
        return ResponseEntity.ok(exists);
    }
}