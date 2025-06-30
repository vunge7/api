package com.dvml.api.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Receita{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "data")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date data;


    @Column(name = "inscricao_id")
    private long inscricaoId;
    @Column(name = "usuario_id")
    private long usuarioId;

    @Column(name = "inicio_tratamento")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date inicioTratamento;

    @Column(name = "fim_tratamento")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date fimTratamento;

    @Column(name = "status", nullable = false, columnDefinition = "BOOLEAN DEFAULT TRUE")
    private Boolean status = true;

}
