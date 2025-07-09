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

    // Construtor padrão para PainelService e UsuarioPainelService
    public PainelDTO() {
    }
}