package com.dvml.api.entity;

import com.dvml.api.util.CondicaoInscricao;
import com.dvml.api.util.EncaminhamentoInscricao;
import com.dvml.api.util.EstadoInscricao;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDate;
import java.util.Date;

import static com.dvml.api.util.EstadoInscricao.*;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class Inscricao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "cor_triagem_manchester")
    private String corTriagemManchester;

    @Column(name = "minuto_espera_triagem_manchester")
    private long minutoEsperaTriagemManchester;

    @Column(name = "obs_triagem_manchester")
    private String obsTriagemManchester;

    @Column(name = "data_criacao")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date dataCriacao;
    @Column(name = "data_actualizacao")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date dataActualizacao;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado")
    private EstadoInscricao estadoInscricao;

    @Enumerated(EnumType.STRING)
    @Column(name = "condicao_inscricao")
    private CondicaoInscricao condicaoInscricao;

    @Enumerated(EnumType.STRING)
    @Column(name = "encaminhamento")
    private EncaminhamentoInscricao encaminhamento;
    @Column(name = "paciente_id")
    private long pacienteId;



}
