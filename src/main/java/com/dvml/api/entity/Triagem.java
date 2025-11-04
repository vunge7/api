package com.dvml.api.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "triagem")
@Getter
@Setter
@NoArgsConstructor
public class Triagem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "data_criacao")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date dataCriacao;

    @Column(name = "inscricao_id")
    private Long inscricaoId;

    @Column(name = "usuario_id")
    private Long usuarioId;


    @Column(name = "empresa_id")
    @NotNull(message = "empresa é obrigatória")
    private Long empresaId;
}
