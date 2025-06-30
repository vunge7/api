package com.dvml.api.dto;

import com.dvml.api.entity.Lotes;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class LotesDTO {

    private Long id;

    @NotNull(message = "Usuário ID é obrigatório")
    private Long usuarioId;

    @NotBlank(message = "Designação é obrigatória")
    private String designacao;

    @NotNull(message = "Data de criação é obrigatória")
    private LocalDateTime dataCriacao;

    @NotNull(message = "Data de vencimento é obrigatória")
    private LocalDateTime dataVencimento;

    private LocalDateTime dataEntrada;

    @NotNull(message = "Status é obrigatório")
    private Boolean status;

    // Método para converter DTO em Entity
    public Lotes toEntity() {
        Lotes entity = new Lotes();
        entity.setId(this.id);
        entity.setUsuarioId(this.usuarioId);
        entity.setDesignacao(this.designacao);
        entity.setDataCriacao(this.dataCriacao);
        entity.setDataVencimento(this.dataVencimento);
        entity.setDataEntrada(this.dataEntrada);
        entity.setStatus(this.status);
        return entity;
    }

    // Método para criar DTO a partir de Entity (útil para respostas)
    public static LotesDTO fromEntity(Lotes entity) {
        LotesDTO dto = new LotesDTO();
        dto.setId(entity.getId());
        dto.setUsuarioId(entity.getUsuarioId());
        dto.setDesignacao(entity.getDesignacao());
        dto.setDataCriacao(entity.getDataCriacao());
        dto.setDataVencimento(entity.getDataVencimento());
        dto.setDataEntrada(entity.getDataEntrada());
        dto.setStatus(entity.getStatus());
        return dto;
    }

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getUsuarioId() { return usuarioId; }
    public void setUsuarioId(Long usuarioId) { this.usuarioId = usuarioId; }
    public String getDesignacao() { return designacao; }
    public void setDesignacao(String designacao) { this.designacao = designacao; }
    public LocalDateTime getDataCriacao() { return dataCriacao; }
    public void setDataCriacao(LocalDateTime dataCriacao) { this.dataCriacao = dataCriacao; }
    public LocalDateTime getDataVencimento() { return dataVencimento; }
    public void setDataVencimento(LocalDateTime dataVencimento) { this.dataVencimento = dataVencimento; }
    public LocalDateTime getDataEntrada() { return dataEntrada; }
    public void setDataEntrada(LocalDateTime dataEntrada) { this.dataEntrada = dataEntrada; }
    public Boolean getStatus() { return status; }
    public void setStatus(Boolean status) { this.status = status; }
}