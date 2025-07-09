package com.dvml.api.service;

import com.dvml.api.entity.ResultadoExame;
import com.dvml.api.repository.ResultadoExameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ResultadoExameService {

    @Autowired
    private ResultadoExameRepository repository;

    public ResultadoExame salvar(ResultadoExame resultadoExame) {
        return repository.save(resultadoExame);
    }

    public List<ResultadoExame> listarTodos() {
        return repository.findAll();
    }

    public Optional<ResultadoExame> buscarPorId(Long id) {
        return repository.findById(id);
    }
    public ResultadoExame atualizar(Long id, ResultadoExame novoResultado) {
        return repository.findById(id)
                .map(resultadoExistente -> {
                    resultadoExistente.setPacienteId(novoResultado.getPacienteId());
                    resultadoExistente.setUsuarioId(novoResultado.getUsuarioId());
                    resultadoExistente.setDataResultado(novoResultado.getDataResultado());
                    return repository.save(resultadoExistente);
                })
                .orElseThrow(() -> new RuntimeException("Resultado de exame não encontrado com ID: " + id));
    }


    public void deletar(Long id) {
        repository.deleteById(id);
    }
}
