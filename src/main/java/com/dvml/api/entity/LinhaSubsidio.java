package com.dvml.api.entity;



import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;


@Entity
@Getter
@Setter
@NoArgsConstructor
public class LinhaSubsidio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "funcionario_id", nullable = false)
    private Long funcionarioId;

    @Column(name = "subsidio_id", nullable = false)
    private Long subsidioId;

    @Column(name = "valor", nullable = false)
    private BigDecimal valor;

    @Column(name = "usuario_id")
    private Long usuarioId;
}