package com.dvml.api.entity;


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
public class Procedimento {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "data_criacao")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date dataCricao;

    @Column(name = "usuario_id")
    private long usuarioId;

    @Column(name = "inscricao_id")
    private long inscricaoId;

    @Column(name = "data_actualizacao")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date dataActualizacao;


    @Column(name = "status", nullable = false, columnDefinition = "BOOLEAN DEFAULT TRUE")
    private Boolean status = true;


}
