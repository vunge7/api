package com.dvml.api.entity;

import com.dvml.api.util.EstadoConsulta;
import com.dvml.api.util.EstadoInscricao;
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
public class Consulta{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "data_consulta")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date dataConsulta;
    @Column(name = "queixas", length = 250)
    private String queixas;
    @Column(name = "motivo_consulta",  length = 250)
    private String motivoConsulta;
    @Column(name = "historia_clinica",  length = 250)
    private String historiaClinica;
    @Column(name = "exame_fisico",  length = 250)
    private String exameFisico;
    @Column(name = "exame_objectivo_geral",  length = 250)
    private String exameObjectivoGeral;
    @Column(name = "hipotse_diagnostico",  length = 250)
    private String hipotseDiagnostico;
    @Column(name = "historia_doenca_actual",  length = 250)
    private String historiaDoencaActual;
    @Column(name =  "diagnostico_definitivo",  length = 250)
    private String diagnosticoDefinitivo;
    @Column(name = "especialidade",  length = 250)
    private String especialidade;

    @Column(name = "recomendacoes",  length = 250)
    private String recomendacoes;
    @Column(name = "obs_para_alta_medica",  length = 250)
    private String obsParaAltaMedica;
    @Column(name = "diagnostico_ao_internamento",  length = 250)
    private String diagnosticoAoInternamento;
    @Column(name = "receita", length = 1000)
    private String receita;

    @Column(name = "diagnostico_inicial", length = 200)
    private String diagnosticoInicial;

    @Column(name = "diagnostico_final", length = 200)
    private String diagnosticoFinal;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado_consulta")
    private EstadoConsulta estadoConsulta;
    @Column(name = "usuario_id")
    private long usuarioId;

    @Column(name = "inscricao_id")
    private long inscricaoId;

}
