package com.dvml.api.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DepartamentoDTO {

    private Long id;

    @NotEmpty(message = "Descrição é obrigatória.")
    private String descricao;

    @NotNull(message = "empresa é obrigatória")
    private Long empresaId; // ✅ novo campo

}
