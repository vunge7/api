package com.dvml.api.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LinhaSubsidioDTO {
    private Long id;

    @NotNull(message = "O ID do subsídio é obrigatório")
    private Long subsidioId;

    @NotNull(message = "O ID do funcionário é obrigatório")
    private Long funcionarioId;

    @NotNull(message = "O valor é obrigatório")
    @Positive(message = "O valor deve ser positivo")
    private Double valor;

    private Long usuarioId;
}