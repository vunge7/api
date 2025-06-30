package com.dvml.api.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class LinhaAgendaDTO {
    private Long id;
    private Long agendaId;
    private Long consultaId;
    private Long funcionarioId;
    private Long pacienteId;
    private Date dataRealizacao;
    private Boolean status;

    public LinhaAgendaDTO(Long id, Long agendaId, Long consultaId, Long funcionarioId, Long pacienteId, Date dataRealizacao, Boolean status) {
        this.id = id;
        this.agendaId = agendaId;
        this.consultaId = consultaId;
        this.funcionarioId = funcionarioId;
        this.pacienteId = pacienteId;
        this.dataRealizacao = dataRealizacao;
        this.status = status;
    }
}