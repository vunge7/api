package com.dvml.api.dto;

import com.dvml.api.entity.Armazem;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ArmazemDTO {

    private Long id;

    @NotBlank(message = "Designação é obrigatória")
    @Size(min = 3, max = 100, message = "Designação deve ter entre 3 e 100 caracteres")
    private String designacao;

    private Long empresaId; // Apenas este campo

    // Converte DTO → Entity
    public Armazem toEntity() {
        Armazem armazem = new Armazem();
        armazem.setId(this.id);
        armazem.setDesignacao(this.designacao);
        armazem.setEmpresaId(this.empresaId);
        return armazem;
    }

    // Entity → DTO
    public static ArmazemDTO fromEntity(Armazem armazem) {
        ArmazemDTO dto = new ArmazemDTO();
        dto.setId(armazem.getId());
        dto.setDesignacao(armazem.getDesignacao());
        dto.setEmpresaId(armazem.getEmpresaId());
        return dto;
    }
}