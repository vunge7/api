package com.dvml.api.entity;

import jakarta.persistence.*;
import java.util.Date;

@Entity
public class UsuarioPainel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long painelId; // Campo simples, não é uma relação @ManyToOne
    private Long usuarioId;
    private Long usuarioCadastradoId;
    private Date dataRegistro;

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getPainelId() { return painelId; }
    public void setPainelId(Long painelId) { this.painelId = painelId; }
    public Long getUsuarioId() { return usuarioId; }
    public void setUsuarioId(Long usuarioId) { this.usuarioId = usuarioId; }
    public Long getUsuarioCadastradoId() { return usuarioCadastradoId; }
    public void setUsuarioCadastradoId(Long usuarioCadastradoId) { this.usuarioCadastradoId = usuarioCadastradoId; }
    public Date getDataRegistro() { return dataRegistro; }
    public void setDataRegistro(Date dataRegistro) { this.dataRegistro = dataRegistro; }
}