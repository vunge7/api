package com.dvml.api.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class ResultadoExame {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "paciente_id")
    private Long pacienteId;

    @Column(name = "usuario_id")
    private Long usuarioId;

    @Column(name = "data_resultado", nullable = false)
    @NotNull(message = "A data de resultado é obrigatória.")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date dataResultado;

}
