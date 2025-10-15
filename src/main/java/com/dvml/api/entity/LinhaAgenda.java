package com.dvml.api.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Setter
@Getter
@NoArgsConstructor
@Table(name = "linha_agenda")
public class LinhaAgenda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "agenda_id", nullable = false)
    private Long agendaId;

    @Column(name = "produto_id", nullable = false)
    private Long produtoId; // 🔹 Serviço (ex: tipo de consulta)

    @Column(name = "consulta_id")
    private Long consultaId; // 🔹 Registro específico da consulta (pode ser nulo inicialmente)

    @Column(name = "funcionario_id", nullable = false)
    private Long funcionarioId;

    @Column(name = "paciente_id", nullable = false)
    private Long pacienteId;

    @Column(name = "data_realizacao", nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataRealizacao;

    @Column(name = "confirmacao", columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean confirmacao = false;

    @Column(name = "empresa_id")
    private Long empresaId;

    @Column(name = "status", nullable = false, columnDefinition = "BOOLEAN DEFAULT TRUE")
    private Boolean status = true;
}
