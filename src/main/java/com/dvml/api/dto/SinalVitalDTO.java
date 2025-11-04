package com.dvml.api.dto;

import com.dvml.api.util.Campo;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class SinalVitalDTO {

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date dataCriacao;
    private String valor;
    private Campo campo;

    // Construtor que recebe campo como String (do banco) e converte para enum
    public SinalVitalDTO(Date dataCriacao, String valor, String campo) {
        this.dataCriacao = dataCriacao;
        this.valor = valor;
        this.campo = campo != null ? Campo.valueOf(campo) : null;
    }

    public Date getDataCriacao() { return dataCriacao; }
    public String getValor() { return valor; }
    public Campo getCampo() { return campo; }
}
