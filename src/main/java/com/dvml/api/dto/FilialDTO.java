package com.dvml.api.dto;

import com.dvml.api.entity.Filial;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FilialDTO {

    private Long id;

    @NotBlank(message = "Nome é obrigatório")
    @Size(min = 3, max = 100, message = "Nome deve ter entre 3 e 100 caracteres")
    private String nome;

    @NotBlank(message = "NIF é obrigatório")
    @Size(max = 50, message = "NIF deve ter no máximo 50 caracteres")
    private String nif;

    private boolean status;

    public Filial toEntity() {
        Filial filial = new Filial();
        filial.setId(this.id);
        filial.setNome(this.nome);
        filial.setNif(this.nif);
        filial.setStatus(this.status);
        return filial;
    }

    public static FilialDTO fromEntity(Filial filial) {
        FilialDTO dto = new FilialDTO();
        dto.setId(filial.getId());
        dto.setNome(filial.getNome());
        dto.setNif(filial.getNif());
        dto.setStatus(filial.isStatus());
        return dto;
    }
}