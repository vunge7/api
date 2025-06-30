package com.dvml.api.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Paciente {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private long id;



    @Column(name = "data_cadastro")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date dataCadastro;

    @Column(name = "data_actualizacao")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date dataActualizacao;

    @Column(name = "pessoa_id")
    private long pessoaId;

}
