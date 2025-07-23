package com.dvml.api.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public class PainelPermissaoDTO {
    private Long id;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime dataCriacao;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime dataActualizacao;

    private Long usuarioIdCriacao;
    private Long usuarioIdActualizacao;
    private Long painelId;
    private Long usuarioId;
    private Long filialId;

    // Getters
    public Long getId() {
        return id;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public LocalDateTime getDataActualizacao() {
        return dataActualizacao;
    }

    public Long getUsuarioIdCriacao() {
        return usuarioIdCriacao;
    }

    public Long getUsuarioIdActualizacao() {
        return usuarioIdActualizacao;
    }

    public Long getPainelId() {
        return painelId;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public Long getFilialId() {
        return filialId;
    }

    // Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public void setDataActualizacao(LocalDateTime dataActualizacao) {
        this.dataActualizacao = dataActualizacao;
    }

    public void setUsuarioIdCriacao(Long usuarioIdCriacao) {
        this.usuarioIdCriacao = usuarioIdCriacao;
    }

    public void setUsuarioIdActualizacao(Long usuarioIdActualizacao) {
        this.usuarioIdActualizacao = usuarioIdActualizacao;
    }

    public void setPainelId(Long painelId) {
        this.painelId = painelId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public void setFilialId(Long filialId) {
        this.filialId = filialId;
    }
}