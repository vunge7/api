package com.dvml.api.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "exame")
@Getter
@Setter
@NoArgsConstructor
public class Exame {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Requisição de exame é obrigatória")
    @Column(name = "requisicao_exame_id", nullable = false)
    private Long requisicaoExameId;

    @NotNull(message = "Paciente é obrigatório")
    @Column(name = "paciente_id", nullable = false)
    private Long pacienteId;

    @NotNull(message = "Médico é obrigatório")
    @Column(name = "medico_id", nullable = false)
    private Long medicoId;

    @NotNull(message = "Tipo de exame é obrigatório")
    @Column(name = "tipo_exame_id", nullable = false)
    private Long tipoExameId;

    @NotNull(message = "Armazém é obrigatório")
    @Column(name = "armazem_id", nullable = false)
    private Long armazemId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @NotNull(message = "Data de realização é obrigatória")
    @Column(name = "data_realizacao", nullable = false)
    private Date dataRealizacao;

    @Column(name = "resultado_qualitativo")
    @Enumerated(EnumType.STRING)
    private ResultadoQualitativo resultadoQualitativo;

    @Column(name = "resultado_quantitativo")
    private Double resultadoQuantitativo;

    @Column(name = "status_amostra")
    @Enumerated(EnumType.STRING)
    private StatusAmostra statusAmostra;

    @Column(name = "observacoes", length = 500)
    private String observacoes;

    public enum ResultadoQualitativo {
        POSITIVO, NEGATIVO, INCONCLUSIVO
    }

    public enum StatusAmostra {
        COLETADA, EM_ANALISE, ANALISADA, ENTREGUE, DESCARTADA
    }
}