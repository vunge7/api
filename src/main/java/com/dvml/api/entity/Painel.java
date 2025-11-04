package com.dvml.api.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
public class Painel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String descricao;

<<<<<<< HEAD
=======
    @Column(name = "empresa_id")
    @NotNull(message = "empresa é obrigatória")
    private Long empresaId; // ✅ Adicionado
>>>>>>> e287a24d4532ec75755395590a1b99c14a90b7de

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }


}
