package com.dvml.api.dto;

import com.dvml.api.entity.TipoExame;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TipoExameDTO {
    private Long id;

    @NotBlank(message = "Nome do tipo de exame é obrigatório")
    @Size(min = 3, max = 100, message = "Nome do tipo de exame deve ter entre 3 e 100 caracteres")
    private String nome;

    @Size(max = 500, message = "Descrição deve ter no máximo 500 caracteres")
    private String descricao;

    @NotBlank(message = "Tipo de amostra é obrigatório")
    @Size(max = 50, message = "Tipo de amostra deve ter no máximo 50 caracteres")
    private String tipoAmostra;

    private Long empresaId; // <--- campo empresaId adicionado

    public TipoExame toEntity() {
        TipoExame tipoExame = new TipoExame();
        tipoExame.setId(this.id);
        tipoExame.setNome(this.nome);
        tipoExame.setDescricao(this.descricao);
        tipoExame.setTipoAmostra(this.tipoAmostra);
        tipoExame.setEmpresaId(this.empresaId); // <--- empresaId convertido para Entity
        return tipoExame;
    }

    public static TipoExameDTO fromEntity(TipoExame tipoExame) {
        TipoExameDTO dto = new TipoExameDTO();
        dto.setId(tipoExame.getId());
        dto.setNome(tipoExame.getNome());
        dto.setDescricao(tipoExame.getDescricao());
        dto.setTipoAmostra(tipoExame.getTipoAmostra());
        dto.setEmpresaId(tipoExame.getEmpresaId()); // <--- empresaId convertido para DTO
        return dto;
    }
}
