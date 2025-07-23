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

    @NotBlank(message = "Província é obrigatória")
    @Size(min = 2, max = 100, message = "Província deve ter entre 2 e 100 caracteres")
    private String provincia;

    @NotBlank(message = "Município é obrigatório")
    @Size(min = 2, max = 100, message = "Município deve ter entre 2 e 100 caracteres")
    private String municipio;

    private boolean status;

    public Filial toEntity() {
        Filial filial = new Filial();
        filial.setId(this.id);
        filial.setNome(this.nome);
        filial.setNif(this.nif);
        filial.setProvincia(this.provincia);
        filial.setMunicipio(this.municipio);
        filial.setStatus(this.status);
        return filial;
    }

    public static FilialDTO fromEntity(Filial filial) {
        FilialDTO dto = new FilialDTO();
        dto.setId(filial.getId());
        dto.setNome(filial.getNome());
        dto.setNif(filial.getNif());
        dto.setProvincia(filial.getProvincia());
        dto.setMunicipio(filial.getMunicipio());
        dto.setStatus(filial.isStatus());
        return dto;
    }
}
