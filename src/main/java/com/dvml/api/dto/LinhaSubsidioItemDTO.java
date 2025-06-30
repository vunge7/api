package com.dvml.api.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class LinhaSubsidioItemDTO {

    private Long id;

    private Long funcionarioId;

    private Long subsidioId;

    @NotBlank(message = "A descrição do subsídio é obrigatória.")
    private String descricaoSubsidio;

    @NotNull(message = "O valor é obrigatório.")
    @DecimalMin(value = "0.0", inclusive = true, message = "O valor deve ser maior ou igual a zero.")
    private BigDecimal valor;

    private Long usuarioId;
}