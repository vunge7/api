package com.dvml.api.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class TriagemDTO {
    private long id;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date dataCriacao;
    private long incricaoId;
    private String paciente;
    private long userId;
    private String nomeUsuario;
    private List<LinhaTriagemDTO> triagens;
}
