package com.dvml.api.dto;

import com.dvml.api.util.EstadoInscricao;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;


@Data
public class InscricaoFullDTO {

    private long inscricaoId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date dataCriacao;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date dataActualizacao;

    private EstadoInscricao estado;
    private long  pacienteId;
    private String nome;
    private String apelido;
    private String encaminhamento;
    private String nomeCompleto;
    private String corTriagemManchester;
    private String condicaoInscricao;
}
