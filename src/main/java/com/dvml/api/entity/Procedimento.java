package com.dvml.api.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
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
    private Long id;

    @Column(name = "data_criacao")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date dataCricao;

    @Column(name = "usuario_id")
    private Long usuarioId;

    @Column(name = "inscricao_id")
    private Long inscricaoId;

    @Column(name = "data_actualizacao")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date dataActualizacao;

    @Column(name = "empresa_id")
    @NotNull(message = "empresa é obrigatória")
    private Long empresaId;


    @Column(name = "status", nullable = false, columnDefinition = "BOOLEAN DEFAULT TRUE")
    private Boolean status = true;


}
