package com.dvml.api.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "armazem")
@Getter
@Setter
@NoArgsConstructor
public class Armazem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "designacao")
    @NotBlank(message = "Designação é obrigatória")
    @Size(min = 3, max = 100, message = "Designação deve ter entre 3 e 100 caracteres")
    private String designacao;

    @Column(name = "filial_id", nullable = false)
    @NotNull(message = "Filial é obrigatória")
    private Long filialId;
}