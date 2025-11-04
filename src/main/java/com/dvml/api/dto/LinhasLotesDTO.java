package com.dvml.api.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LinhasLotesDTO {

    private Long id;
    private Long lotes_id;
    private Long produto_id;
    private Long armazem_id;
    private Integer quantidade;
    @NotNull(message = "empresa é obrigatória")
    private Long empresaId;
}