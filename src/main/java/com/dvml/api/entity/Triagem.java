package com.dvml.api.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class Triagem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  long id;
    @Column(name = "data_criacao")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date dataCriacao;
    @Column(name = "inscricao_id")
    private long inscricaoId;
    @Column(name = "usuario_id")
    private long usuarioId;


}
