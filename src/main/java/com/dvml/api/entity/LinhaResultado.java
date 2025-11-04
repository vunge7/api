package com.dvml.api.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class LinhaResultado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "exame_id")
    private Long exameId;

    @Column(name = "valor_referencia")
    private BigDecimal valorReferencia;

    @Column(name = "unidade_id")
    private Long unidadeId;

    @Column(name = "resultado_id")
    private Long resultadoId;

    @Column(name = "observacao")
    private String observacao;

    @Column(name = "empresa_id")
    @NotNull(message = "empresa é obrigatória")
    private Long empresaId;
}