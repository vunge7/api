package com.dvml.api.service;

import com.dvml.api.dto.ConsultaSimpleDTO;
import com.dvml.api.entity.Consulta;
import com.dvml.api.repository.ConsultaRepository;
import com.dvml.api.repository.InscricaoRepository;
import com.dvml.api.util.EstadoConsulta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ConsultaService {

    @Autowired
    private ConsultaRepository repo;

    @Autowired
    private InscricaoRepository inscricaoRepository;

    // Converter Consulta → ConsultaSimpleDTO
    public ConsultaSimpleDTO convertEntityToDto(Consulta consulta) {
        ConsultaSimpleDTO dto = new ConsultaSimpleDTO();
        dto.setId(consulta.getId());
        dto.setMotivoConsulta(consulta.getMotivoConsulta());
        dto.setHistoriaClinica(consulta.getHistoriaClinica());
        dto.setExameFisico(consulta.getExameFisico());
        dto.setDiagnosticoInicial(consulta.getDiagnosticoInicial());
        dto.setDiagnosticoFinal(consulta.getDiagnosticoFinal());
        dto.setReceita(consulta.getReceita());
        dto.setExamesComplementares(null); // Pode ser ajustado futuramente
        dto.setEstadoConsulta(consulta.getEstadoConsulta());
        return dto;
    }

    // Criar nova consulta
    public Consulta adicionar(Consulta consulta) {
        return repo.save(consulta);
    }

    // Atualizar consulta existente
    public Consulta update(Consulta consulta) {
        Consulta c = repo.findById(consulta.getId())
                .orElseThrow(() -> new IllegalArgumentException("Consulta não encontrada"));

        c.setDataConsulta(consulta.getDataConsulta());
        c.setQueixas(consulta.getQueixas());
        c.setMotivoConsulta(consulta.getMotivoConsulta());
        c.setHistoriaClinica(consulta.getHistoriaClinica());
        c.setExameFisico(consulta.getExameFisico());
        c.setExameObjectivoGeral(consulta.getExameObjectivoGeral());
        c.setHistoriaDoencaActual(consulta.getHistoriaDoencaActual());
        c.setDiagnosticoDefinitivo(consulta.getDiagnosticoDefinitivo());
        c.setEspecialidade(consulta.getEspecialidade());
        c.setInscricaoId(consulta.getInscricaoId());
        c.setRecomendacoes(consulta.getRecomendacoes());
        c.setObsParaAltaMedica(consulta.getObsParaAltaMedica());
        c.setDiagnosticoAoInternamento(consulta.getDiagnosticoAoInternamento());
        c.setUsuarioId(consulta.getUsuarioId());
        c.setEstadoConsulta(consulta.getEstadoConsulta());
        c.setReceita(consulta.getReceita());
        c.setDiagnosticoInicial(consulta.getDiagnosticoInicial());
        c.setDiagnosticoFinal(consulta.getDiagnosticoFinal());
        c.setEmpresaId(consulta.getEmpresaId());

        return repo.save(c);
    }

    // Listar todas as consultas
    public List<ConsultaSimpleDTO> listarTodos() {
        return repo.findAll().stream()
                .map(this::convertEntityToDto)
                .collect(Collectors.toList());
    }

    // Buscar consulta por ID
    public ConsultaSimpleDTO getConsultaById(long id) {
        Consulta c = repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Consulta não encontrada"));
        return convertEntityToDto(c);
    }

    // Buscar consulta por estado e inscrição (DTO)
    public ConsultaSimpleDTO getConsultaByEstadoAndIdInscricao(String estado, long idInscricao) {
        try {
            EstadoConsulta estadoEnum = EstadoConsulta.valueOf(estado.toUpperCase());
            Consulta c = repo.findByInscricaoIdAndEstadoConsulta(idInscricao, estadoEnum.name());
            if (c == null) throw new IllegalArgumentException("Consulta não encontrada");
            return convertEntityToDto(c);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Estado inválido ou consulta não encontrada");
        }
    }

    // Buscar consulta por estado e inscrição (entidade direta)
    public Consulta getByInscricaoIdAndEstado(Long idInscricao, EstadoConsulta estado) {
        return repo.findByInscricaoIdAndEstadoConsulta(idInscricao, estado.name());
    }

    // Atualizar estado da consulta de ABERTO para FECHADO
    public void updateEstadoCondicaoConsulta(long idInscricao) {
        Consulta c = repo.findByInscricaoIdAndEstadoConsulta(idInscricao, EstadoConsulta.ABERTO.name());
        if (c != null) {
            c.setEstadoConsulta(EstadoConsulta.FECHADO);
            repo.save(c);
        }
    }
}
