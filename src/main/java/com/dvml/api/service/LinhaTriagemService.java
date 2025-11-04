package com.dvml.api.service;

import com.dvml.api.dto.LinhaTriagemDTO;
import com.dvml.api.dto.SinalVitalDTO;
import com.dvml.api.entity.LinhaTriagem;
import com.dvml.api.repository.LinhaTriagemRepositoty;
import com.dvml.api.util.Campo;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.Date;

@Service
public class LinhaTriagemService {

    @Autowired
    private LinhaTriagemRepositoty repo;

    public LinhaTriagemDTO convertEntityToDto(LinhaTriagem entity) {
        LinhaTriagemDTO dto = new LinhaTriagemDTO();
        dto.setCampo(entity.getCampo());
        dto.setValor(entity.getValor());
        dto.setUnidade(entity.getUnidade());
        dto.setTriagemId(entity.getTriagemId());
        dto.setEmpresaId(entity.getEmpresaId());
        return dto;
    }

    public List<LinhaTriagemDTO> listarTodos() {
        return repo.findAll().stream()
                .map(this::convertEntityToDto)
                .collect(Collectors.toList());
    }

    public LinhaTriagem salvar(LinhaTriagem entity) {
        return repo.save(entity);
    }

    public LinhaTriagem atualizar(Long id, LinhaTriagem novo) {
        LinhaTriagem existente = repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("LinhaTriagem com ID " + id + " não encontrada."));
        existente.setCampo(novo.getCampo());
        existente.setValor(novo.getValor());
        existente.setUnidade(novo.getUnidade());
        existente.setTriagemId(novo.getTriagemId());
        existente.setEmpresaId(novo.getEmpresaId());
        return repo.save(existente);
    }

    public void deletar(Long id) {
        if (!repo.existsById(id)) {
            throw new EntityNotFoundException("LinhaTriagem com ID " + id + " não encontrada.");
        }
        repo.deleteById(id);
    }

    // 🔹 Obter sinais vitais por paciente e campo
    public List<SinalVitalDTO> obterSinalVitalPorPaciente(Long pacienteId, Campo campo) {
        List<Object[]> resultados = repo.buscarSinalVitalPorPaciente(pacienteId, campo.name());
        return resultados.stream()
                .map(obj -> new SinalVitalDTO((Date) obj[0], (String) obj[1], (String) obj[2]))
                .collect(Collectors.toList());
    }

    // 🔹 Agrupar todos os sinais de um paciente por Campo
    public Map<Campo, List<SinalVitalDTO>> agruparSinaisPorCampo(Long pacienteId) {
        List<Object[]> resultados = repo.buscarTodosSinaisComCampo(pacienteId);
        List<SinalVitalDTO> sinais = resultados.stream()
                .map(obj -> new SinalVitalDTO((Date) obj[0], (String) obj[1], (String) obj[2]))
                .collect(Collectors.toList());

        return sinais.stream()
                .collect(Collectors.groupingBy(SinalVitalDTO::getCampo));
    }
}
