package com.dvml.api.dto;

import com.dvml.api.entity.Exame;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class ExameDTO {
    private Long id;

    @NotNull(message = "Requisição de exame é obrigatória")
    private Long requisicaoExameId;

    @NotNull(message = "Paciente é obrigatório")
    private Long pacienteId;

    private String nomePaciente;

    @NotNull(message = "Médico é obrigatório")
    private Long medicoId;

    private String nomeMedico;

    @NotNull(message = "Tipo de exame é obrigatório")
    private Long tipoExameId;

    private String nomeTipoExame;

    @NotNull(message = "Armazém é obrigatório")
    private Long armazemId;

    private String nomeArmazem;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @NotNull(message = "Data de realização é obrigatória")
    private Date dataRealizacao;

    private Exame.ResultadoQualitativo resultadoQualitativo;

    private Double resultadoQuantitativo;

    private Exame.StatusAmostra statusAmostra;

    private String observacoes;

    public Exame toEntity() {
        Exame exame = new Exame();
        exame.setId(this.id);
        exame.setRequisicaoExameId(this.requisicaoExameId);
        exame.setPacienteId(this.pacienteId);
        exame.setMedicoId(this.medicoId);
        exame.setTipoExameId(this.tipoExameId);
        exame.setArmazemId(this.armazemId);
        exame.setDataRealizacao(this.dataRealizacao);
        exame.setResultadoQualitativo(this.resultadoQualitativo);
        exame.setResultadoQuantitativo(this.resultadoQuantitativo);
        exame.setStatusAmostra(this.statusAmostra);
        exame.setObservacoes(this.observacoes);
        return exame;
    }

    public static ExameDTO fromEntity(Exame exame, String nomePaciente, String nomeMedico, String nomeTipoExame, String nomeArmazem) {
        ExameDTO dto = new ExameDTO();
        dto.setId(exame.getId());
        dto.setRequisicaoExameId(exame.getRequisicaoExameId());
        dto.setPacienteId(exame.getPacienteId());
        dto.setNomePaciente(nomePaciente);
        dto.setMedicoId(exame.getMedicoId());
        dto.setNomeMedico(nomeMedico);
        dto.setTipoExameId(exame.getTipoExameId());
        dto.setNomeTipoExame(nomeTipoExame);
        dto.setArmazemId(exame.getArmazemId());
        dto.setNomeArmazem(nomeArmazem);
        dto.setDataRealizacao(exame.getDataRealizacao());
        dto.setResultadoQualitativo(exame.getResultadoQualitativo());
        dto.setResultadoQuantitativo(exame.getResultadoQuantitativo());
        dto.setStatusAmostra(exame.getStatusAmostra());
        dto.setObservacoes(exame.getObservacoes());
        return dto;
    }
}