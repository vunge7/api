package com.dvml.api.dto;

import com.dvml.api.util.EstadoConsulta;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Data
@Getter
@Setter
@NoArgsConstructor
public class ConsultaSimpleDTO {
    private long id;
    private String motivoConsulta;
    private String historiaClinica;
    private String exameFisico;
    private String receita;
    private String diagnosticoInicial;
    private String diagnosticoFinal;
    @NotNull(message = "empresa é obrigatória")
    private long empresaId;
    private List<String> examesComplementares;
    private EstadoConsulta estadoConsulta;

}
