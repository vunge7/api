package com.dvml.api.dto;

import com.dvml.api.util.EstadoUsuario;
import com.dvml.api.util.TipoUsuario;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioDTO {
    private Long id;
    private Long funcionarioId;
    private Long funcaoId;
    private String userName;
    private String senha;
    private String numeroOrdem;
    private EstadoUsuario estadoUsuario;
    private TipoUsuario tipoUsuario;
    private String ip;
}