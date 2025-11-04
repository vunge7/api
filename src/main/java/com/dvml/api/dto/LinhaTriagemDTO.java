package com.dvml.api.dto;

import com.dvml.api.util.Campo;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LinhaTriagemDTO {

    @Enumerated(EnumType.STRING)
    private Campo campo;
    private String valor;
    private String unidade;
    private long triagemId;
    @NotNull(message = "empresa é obrigatória")
    private Long empresaId;
}
