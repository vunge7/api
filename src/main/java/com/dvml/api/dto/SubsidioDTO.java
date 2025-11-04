package com.dvml.api.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubsidioDTO {

    private Long id;

    @NotNull(message = "A descrição do subsídio é obrigatória.")
    private String descricao;


    @NotNull(message = "empresa é obrigatória")
    private Long empresaId;
}