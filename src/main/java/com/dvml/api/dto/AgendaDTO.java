package com.dvml.api.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@Data
@NoArgsConstructor
public class AgendaDTO {
    private Long id;
    private String descricao;

    @NotNull(message = "empresa é obrigatória")
    private Long empresaId; // ✅ novo campo
    private List<LinhaAgendaDTO> linhas;

    public AgendaDTO(Long id, String descricao, Long empresaId, List<LinhaAgendaDTO> linhas) {
        this.id = id;
        this.descricao = descricao;
        this.empresaId = empresaId; // ✅ inicializa o novo campo
        this.linhas = linhas;
    }
}
