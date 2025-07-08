package com.dvml.api.entity;

import jakarta.persistence.*;
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
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "exame_id")
    private Long exameId;

    @Column(name = "valor_referencia")
    private BigDecimal valorReferencia;

    @Column(name = "unidade_id")
    private Long unidadeId;
}
