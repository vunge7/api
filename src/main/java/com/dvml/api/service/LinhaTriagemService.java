package com.dvml.api.service;

import com.dvml.api.dto.LinhaTriagemDTO;
import com.dvml.api.dto.SinalVitalDTO;
import com.dvml.api.entity.LinhaTriagem;
import com.dvml.api.repository.LinhaTriagemRepositoty;
import com.dvml.api.util.Campo;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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
        return repo.save(existente);
    }

    public void deletar(Long id) {
        if (!repo.existsById(id)) {
            throw new EntityNotFoundException("LinhaTriagem com ID " + id + " não encontrada.");
        }
        repo.deleteById(id);
    }

    public List<SinalVitalDTO> obterSinalVitalPorPaciente(Long pacienteId, Campo campo) {
        return repo.buscarSinalVitalPorPaciente(pacienteId, campo);
    }

    public List<SinalVitalDTO> obterTodosSinaisPorPacienteNativo(Long pacienteId) {
        List<Object[]> resultados = repo.buscarTodosSinaisPorPacienteNativo(pacienteId);
        return resultados.stream().map(obj -> {
            Date data = (Date) obj[0];
            String valor = (String) obj[1];
            return new SinalVitalDTO(data, valor);
        }).collect(Collectors.toList());
    }
}
