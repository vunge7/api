package com.dvml.api.dto;

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
    private Long empresaId; // ✅ novo campo
    private List<LinhaAgendaDTO> linhas;

    public AgendaDTO(Long id, String descricao, Long empresaId, List<LinhaAgendaDTO> linhas) {
        this.id = id;
        this.descricao = descricao;
        this.empresaId = empresaId; // ✅ inicializa o novo campo
        this.linhas = linhas;
    }
}
