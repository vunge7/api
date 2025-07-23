package com.dvml.api.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "filial")
@Getter
@Setter
@NoArgsConstructor
public class Filial {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome", nullable = false, unique = true)
    @NotBlank(message = "Nome é obrigatório")
    @Size(min = 3, max = 100, message = "Nome deve ter entre 3 e 100 caracteres")
    private String nome;

    @Column(name = "nif", unique = true)
    @NotBlank(message = "NIF é obrigatório")
    @Size(max = 50, message = "NIF deve ter no máximo 50 caracteres")
    private String nif;

    @Column(name = "status", nullable = false)
    private boolean status = true;

    @Column(name = "provincia", nullable = false)
    @NotBlank(message = "Província é obrigatória")
    @Size(min = 2, max = 100, message = "Província deve ter entre 2 e 100 caracteres")
    private String provincia;

    @Column(name = "municipio", nullable = false)
    @NotBlank(message = "Município é obrigatório")
    @Size(min = 2, max = 100, message = "Município deve ter entre 2 e 100 caracteres")
    private String municipio;
}
