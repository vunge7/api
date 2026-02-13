package com.dvml.api.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PacienteEmpresaDTO {

    private Long id;

    @NotNull(message = "empresa é obrigatória")
    private Long empresaId;

    @NotNull(message = "paciente é obrigatório")
    private Long pacienteId;
}