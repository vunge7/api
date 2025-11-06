package com.dvml.api.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PainelDTO {
    private Long id;

    @NotNull(message = "A descrição do painel é obrigatória.")
    private String descricao;

<<<<<<< HEAD
=======

>>>>>>> 1ea99d0ed9a47cde5a2161e81ce430d803c8d50e
    // Construtor padrão
    public PainelDTO() {
    }
}
