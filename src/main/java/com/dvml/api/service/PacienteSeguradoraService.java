package com.dvml.api.service;



import com.dvml.api.entity.PacienteSeguradora;
import com.dvml.api.entity.SourceDocument;
import com.dvml.api.repository.PacienteSeguradoraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Service
public class   PacienteSeguradoraService {

    @Autowired
    private PacienteSeguradoraRepository repo;


    public List<PacienteSeguradora> listarTodosPacientesSeguradoras() {
        return repo.findAll();
    }


    public PacienteSeguradora getPacienteSeguradoraById(long id) {
        return repo.findById(id).get();
    }

    public ResponseEntity<String> criar(PacienteSeguradora pacienteSeguradora) {

        Optional<PacienteSeguradora> seguradoraIdExistente = repo.findByPacienteIdAndSeguradoraId(pacienteSeguradora.getPacienteId(), pacienteSeguradora.getSeguradoraId());

        if (seguradoraIdExistente.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Já existe um registro para este paciente nesta seguradora: " + pacienteSeguradora.getSeguradoraId());
        }

        if (Objects.nonNull(repo.save(pacienteSeguradora))) {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("Paciente registrado com sucesso na seguradora " + pacienteSeguradora.getSeguradoraId());
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Falha ao criar o registro.");

    }

    public ResponseEntity<String> update(PacienteSeguradora pacienteSeguradora) {
        PacienteSeguradora pacienteSeguradoraToUpdate = repo.findById(pacienteSeguradora.getId()).get();
        pacienteSeguradoraToUpdate.setPacienteId(pacienteSeguradora.getPacienteId());
        pacienteSeguradoraToUpdate.setSeguradoraId(pacienteSeguradora.getSeguradoraId());
        pacienteSeguradoraToUpdate.setId(pacienteSeguradora.getId());
        pacienteSeguradoraToUpdate.setDataCricao(pacienteSeguradora.getDataCricao());
        pacienteSeguradoraToUpdate.setDataActualizacao(pacienteSeguradora.getDataActualizacao());
        pacienteSeguradoraToUpdate.setUsuarioIdAtualizacao(pacienteSeguradora.getUsuarioIdAtualizacao());
        pacienteSeguradoraToUpdate.setUsuarioIdCricao(pacienteSeguradora.getUsuarioIdCricao());

        if (Objects.nonNull(repo.save(pacienteSeguradoraToUpdate))) {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("pacienteseguradora editado com sucesso!");
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Falha ao editar o PacienteSeguradora.");
    }



    public List<PacienteSeguradora>  getAllSeguradorasByIdPaciente(long id){
        return repo.findSeguradorasByIdPaciente(id);
    }

        public void deletPacienteSeguradora(long id) {
            if (repo.existsById(id)) {
                repo.deleteById(id);
            } else {
                throw new IllegalArgumentException("PacienteSeguradora não encontrado com o ID: " + id);
            }

    }

}

