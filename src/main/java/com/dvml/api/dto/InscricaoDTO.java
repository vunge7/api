package com.dvml.api.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDate;
import java.util.Date;


@Data
@Getter
public class InscricaoDTO {

    private long inscricaoId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date dataCriacao;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date dataActualizacao;

    private  String estado;
    private  String condicaoInscricao;
    private PacienteDTO paciente;

}
