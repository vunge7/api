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
public class Seguradora {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;
    @Column(name = "nome")
    private String nome;
    @Column(name = "telefone")
    private String telefone;
    @Column(name = "endereco")
    private String endereco;
    @Column(name = "nif")
    private String nif;


    @Column(name = "empresa_id")
    @NotNull(message = "empresa é obrigatória")
    private Long empresaId;

    @Column(name = "status", nullable = false, columnDefinition = "BOOLEAN DEFAULT TRUE")
    private Boolean status = true;
}
