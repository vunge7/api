package com.dvml.api.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class SinalVitalDTO {

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date dataCriacao;
    private String valor;

    public SinalVitalDTO(Date dataCriacao, String valor) {
        this.dataCriacao = dataCriacao;
        this.valor = valor;
    }

    public Date getDataCriacao() {
        return dataCriacao;
    }

    public String getValor() {
        return valor;
    }
}
