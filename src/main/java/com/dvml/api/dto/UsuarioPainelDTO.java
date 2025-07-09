package com.dvml.api.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class UsuarioPainelDTO {

    private Long id;

    @NotNull(message = "O ID do painel é obrigatório.")
    private Long painelId;

    @NotNull(message = "O ID do usuário cadastrado é obrigatório.")
    private Long usuarioCadastradoId;

    @NotNull(message = "O ID do usuário é obrigatório.")
    private Long usuarioId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date dataRegistro;
}
