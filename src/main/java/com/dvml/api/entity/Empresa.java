package com.dvml.api.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Empresa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  long id;
    @Column(name = "nome")
    private String nome;
    @Column(name = "nif")
    private String nif;
    @Column(name = "telefone")
    private String telefone;

    @Column(name = "seguradora_id")
    private String seguradoraId;
}
