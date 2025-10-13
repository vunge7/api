package com.dvml.api.dto;

import com.dvml.api.util.Genero;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class AgendaAppDTO {

    // 🔹 Dados da Pessoa
    private String nome;

    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "NIF deve ser alfanumérico")
    private String nif;

    private String telefone;
    private Genero genero;

    // 🔹 Dados da Consulta (informados pelo app)
    private Long produtoId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date dataConsulta;
}
