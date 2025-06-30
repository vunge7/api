package com.dvml.api.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "linha_agenda") // Adicionei o nome da tabela explicitamente para clareza
@Setter
@Getter
@NoArgsConstructor
public class LinhaAgenda {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Corrigido de 'long' para 'Long' (tipo wrapper é mais comum com JPA)

    @Column(name = "agenda_id", nullable = false)
    private Long agendaId;

    @Column(name = "consulta_id", nullable = false)
    private Long consultaId;

    @Column(name = "funcionario_id", nullable = false)
    private Long funcionarioId;

    @Column(name = "paciente_id", nullable = false)
    private Long pacienteId;

    @Column(name = "data_realizacao", nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss") // Especifica o formato esperado
    @Temporal(TemporalType.TIMESTAMP) // Garante que é tratado como timestamp
    private Date dataRealizacao;

    @Column(name = "status", nullable = false, columnDefinition = "BOOLEAN DEFAULT TRUE")
    private Boolean status = true;
}
