package com.dvml.api.controller;

import com.dvml.api.dto.AuthRequestDTO;
import com.dvml.api.dto.AuthResponseDTO;
import com.dvml.api.dto.FilialDTO;
import com.dvml.api.dto.UsuarioDTO;
import com.dvml.api.entity.Usuario;
import com.dvml.api.service.AuthService;
import com.dvml.api.service.PainelPermissaoService;
import com.dvml.api.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private AuthService authService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private PainelPermissaoService painelPermissaoService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody AuthRequestDTO authRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
        );


        Usuario usuario = usuarioService.findByUserName(authRequest.getUsername())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        String token = authService.authenticate(usuario, authRequest.getPassword());

        List<FilialDTO> filiaisDTOByUsuarioId = painelPermissaoService.getFiliaisDTOByUsuarioId(usuario.getId());


        System.out.println("Filiais associadas ao usuário " + usuario.getUserName() + ": " + filiaisDTOByUsuarioId);
        AuthResponseDTO response = new AuthResponseDTO();
        response.setToken(token);
        response.setId(usuario.getId());
        response.setUsername(usuario.getUserName());
        response.setTipo(usuario.getTipoUsuario().name());
        response.setFiliais(filiaisDTOByUsuarioId);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/usuarios/cadastrar")
    public ResponseEntity<?> cadastrar(@RequestBody UsuarioDTO usuarioDTO) {
        try {
            UsuarioDTO responseDTO = usuarioService.cadastrarUsuario(usuarioDTO);
            return ResponseEntity.ok(responseDTO);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Erro ao cadastrar usuário: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao cadastrar usuário: " + e.getMessage());
        }
    }
}