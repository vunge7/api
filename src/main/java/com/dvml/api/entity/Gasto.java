package com.dvml.api.entity;


import com.dvml.api.util.EstadoConvertido;
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
public class Gasto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  long id;

    @Column(name = "data_criacao")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date dataCriacao;

    @Column(name = "inscricao_id")
    private long inscricaoId;
    @Column(name = "doc_ref")
    private String docRef;

    @Column(name = "doc")
    private String doc;

    @Enumerated(EnumType.STRING)
    @Column(name = "convertido")
    private EstadoConvertido convertido;


    @Column(name = "status", nullable = false, columnDefinition = "BOOLEAN DEFAULT TRUE")
    private Boolean status = true;

}
