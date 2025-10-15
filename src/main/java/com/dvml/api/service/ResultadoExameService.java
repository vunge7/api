package com.dvml.api.service;

import com.dvml.api.entity.ResultadoExame;
import com.dvml.api.repository.ResultadoExameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Objects;

@Service
public class ResultadoExameService {

    @Autowired
    private ResultadoExameRepository repository;

    // Salvar novo resultado de exame
    public ResultadoExame salvar(ResultadoExame resultadoExame) {
        return repository.save(resultadoExame); // empresaId será salvo se preenchido
    }

    // Listar todos os resultados de exame
    public List<ResultadoExame> listarTodos() {
        return repository.findAll();
    }

    // Buscar resultado de exame por ID
    public Optional<ResultadoExame> buscarPorId(Long id) {
        return repository.findById(id);
    }

    // Atualizar um resultado de exame existente
    public ResponseEntity<String> atualizar(Long id, ResultadoExame novoResultado) {
        Optional<ResultadoExame> optionalResultado = repository.findById(id);

        if (!optionalResultado.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Resultado de exame não encontrado com ID: " + id);
        }

        ResultadoExame resultadoExistente = optionalResultado.get();
        resultadoExistente.setPacienteId(novoResultado.getPacienteId());
        resultadoExistente.setUsuarioId(novoResultado.getUsuarioId());
        resultadoExistente.setDataResultado(novoResultado.getDataResultado());
        resultadoExistente.setEmpresaId(novoResultado.getEmpresaId()); // <--- empresaId adicionado

        if (Objects.nonNull(repository.save(resultadoExistente))) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body("Resultado de exame atualizado com sucesso!");
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Falha ao atualizar o resultado de exame.");
    }

    // Deletar resultado de exame
    public ResponseEntity<String> deletar(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK)
                    .body("Resultado de exame deletado com sucesso!");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao deletar o resultado de exame.");
        }
    }
}
