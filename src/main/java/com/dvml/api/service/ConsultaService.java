package com.dvml.api.service;


import com.dvml.api.dto.ConsultaSimpleDTO;
import com.dvml.api.entity.Consulta;
import com.dvml.api.repository.ConsultaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ConsultaService {
    @Autowired
    private ConsultaRepository repo;


    public ConsultaSimpleDTO convertEntityToDto(Consulta consulta){
        ConsultaSimpleDTO consultaDTO = new ConsultaSimpleDTO();
        consultaDTO.setId(consulta.getId());
        consultaDTO.setMotivoConsulta(consulta.getMotivoConsulta());
        consultaDTO.setHistoriaClinica(consulta.getHistoriaClinica());
        consultaDTO.setExameFisico(consulta.getExameFisico());
        consultaDTO.setDiagnosticos(null);
        consultaDTO.setReceita(consulta.getReceita());
        consultaDTO.setExamesComplementares(null);
        return consultaDTO;
    }


    public Consulta adicionar(Consulta consulta){
        return  repo.save(consulta);
    }

    public Consulta update(Consulta consulta){
        Consulta consultaToUpdate = repo.findById(consulta.getId()).get();
        consultaToUpdate.setDataConsulta(consulta.getDataConsulta());
        consultaToUpdate.setQueixas(consulta.getQueixas());
        consultaToUpdate.setMotivoConsulta(consulta.getMotivoConsulta());
        consultaToUpdate.setHistoriaClinica(consulta.getHistoriaClinica());
        consultaToUpdate.setExameFisico(consulta.getExameFisico());
        consultaToUpdate.setExameObjectivoGeral(consulta.getExameObjectivoGeral());
        consultaToUpdate.setHistoriaDoencaActual(consulta.getHistoriaDoencaActual());
        consultaToUpdate.setDiagnosticoDefinitivo(consulta.getDiagnosticoDefinitivo());
        consultaToUpdate.setEspecialidade(consulta.getEspecialidade());
        consultaToUpdate.setInscricaoId( consulta.getInscricaoId());
        consultaToUpdate.setRecomendacoes(consulta.getRecomendacoes());
        consultaToUpdate.setObsParaAltaMedica(consulta.getObsParaAltaMedica());
        consultaToUpdate.setMotivoConsulta(consulta.getMotivoConsulta());
        consultaToUpdate.setDiagnosticoAoInternamento(consulta.getDiagnosticoAoInternamento());
        consultaToUpdate.setUsuarioId(consulta.getUsuarioId());
        consultaToUpdate.setEstadoConsulta(consulta.getEstadoConsulta());
        consultaToUpdate.setReceita(consulta.getReceita());

        return  repo.save(consultaToUpdate);
    }

    public List<ConsultaSimpleDTO> listarTodos(){
        return repo.findAll().stream()
                .map(this::convertEntityToDto)
                .collect(Collectors.toList());
    }

  public ConsultaSimpleDTO getConsultaById(long id){
        Consulta consulta =   repo.findById(id).get();
        return  convertEntityToDto(consulta);
    }


    public ConsultaSimpleDTO getConsultaByEstadoAndIdInscricao(String estado, long id){
        Consulta consulta =   repo.getConsultaByEstadoInscricaoAndIdIscricao(estado, id);
        return  convertEntityToDto(consulta);
    }







}

