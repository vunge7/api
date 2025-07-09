package com.dvml.api.entity;

import com.dvml.api.util.Genero;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Pessoa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "nome", nullable = false, length = 100)
    private String nome;

    @Column(name = "apelido", nullable = false)
    private String apelido;

    @Column(name = "nif", nullable = false)
    private String nif;

    @Column(name = "data_nascimento")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date dataNascimento;

    @Column(name = "local_nascimento")
    private String localNascimento;

    @Column(name = "telefone", length = 100)
    private String telefone;

    @Column(name = "email", length = 100)
    private String email;

    @Column(name = "endereco", length = 150)
    private String endereco;

    @Enumerated(EnumType.STRING)
    @Column(name = "genero")
    private Genero genero;

    @Column(name = "bairro")
    private String bairro;

    @Column(name = "estado_civil")
    private String estadoCivil;

    @Column(name = "pai")
    private String pai;

    @Column(name = "mae")
    private String mae;

    @Column(name = "nacionalidade")
    private String nacionalidade;

    @Column(name = "raca")
    private String raca;

    @Column(name = "pais_endereco")
    private String paisEndereco;

    @Column(name = "provincia_endereco")
    private String provinciaEndereco;

    @Column(name = "municipio_endereco")
    private String municipioEndereco;

    @Column(name = "pais_nascimento")
    private String paisNascimento;

    @Column(name = "provincia_nascimento")
    private String provinciaNascimento;

    @Column(name = "municipio_nascimento")
    private String municipioNascimento;

    @Column(name = "profissao")
    private String profissao;

    @Column(name = "habilitacao")
    private String habilitacao;

    @Column(name = "nome_photo")
    private String nomePhoto;
}
