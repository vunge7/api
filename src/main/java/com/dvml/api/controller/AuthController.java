package com.dvml.api.controller;

import com.dvml.api.dto.AuthRequestDTO;
import com.dvml.api.dto.AuthResponseDTO;
import com.dvml.api.dto.UsuarioDTO;
import com.dvml.api.entity.Usuario;
import com.dvml.api.service.AuthService;
import com.dvml.api.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private AuthService authService;

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody AuthRequestDTO authRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
        );

        String token = authService.authenticate(authRequest.getUsername(), authRequest.getPassword());
        Usuario usuario = usuarioService.findByUserName(authRequest.getUsername())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        AuthResponseDTO response = new AuthResponseDTO();
        response.setToken(token);
        response.setId(usuario.getId());
        response.setUsername(usuario.getUserName());
        response.setTipo(usuario.getTipoUsuario().name());

        return ResponseEntity.ok(response);
    }

    @PostMapping("/usuarios/cadastrar")
    public ResponseEntity<?> cadastrar(@Valid @RequestBody UsuarioDTO usuarioDTO) {
        return usuarioService.cadastrarUsuario(usuarioDTO);
    }
}