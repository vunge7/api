package com.dvml.api.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class UnidadeMedidas {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "descricao", length = 100, nullable = false)
    private String descricao;

    @Column(name = "abreviacao", length = 5, nullable = false)
    private String abrevicao;

    @Column(name = "empresa_id")
    @NotNull(message = "empresa é obrigatória")
    private Long empresaId;
}
