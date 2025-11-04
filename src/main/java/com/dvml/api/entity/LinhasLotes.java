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
@Table(name = "linhas_lotes")
public class LinhasLotes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "lotes_id", nullable = false)
    @NotNull(message = "lotes_id é obrigatório")
    private Long lotes_id;

    @Column(name = "produto_id", nullable = false)
    @NotNull(message = "produto_id é obrigatório")
    private Long produto_id;

    @Column(name = "armazem_id")
    @NotNull(message = "ID do armazém é obrigatório")
    private Long armazem_id;

    @Column(name = "quantidade", nullable = false)
    @NotNull(message = "quantidade é obrigatória")
    private Integer quantidade;

    @Column(name = "empresa_id")
    @NotNull(message = "empresa é obrigatória")
    private Long empresaId;
}