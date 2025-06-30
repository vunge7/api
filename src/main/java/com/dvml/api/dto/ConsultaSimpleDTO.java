package com.dvml.api.dto;

import com.dvml.api.util.EstadoConsulta;
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
    private List<String> diagnosticos;
    private List<String> examesComplementares;
    private EstadoConsulta estadoConsulta;

}
