package com.dvml.api.service;
import com.dvml.api.entity.Agenda;
import com.dvml.api.repository.AgendaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
@Service
public class AgendaService {


    @Autowired
    private AgendaRepository repo;


    public List<Agenda> listarTodasAgenda() {

        return repo.findAllOrderByNomeAsc();
    }

    public Agenda getAgendaById(long id) {
        return repo.findById(id).get();
    }

    public Agenda criar(Agenda agenda) {
        return repo.save(agenda);
    }


    public ResponseEntity<String> update(Agenda agenda){
        Agenda agendaToUpdate = repo.findById(agenda.getId()).get();
        agendaToUpdate.setId(agenda.getId());
        agendaToUpdate.setDescricao(agenda.getDescricao());
        agendaToUpdate.setStatus(agenda.getStatus());

        if(Objects.nonNull(repo.save(agendaToUpdate))) {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("Agenda editada com sucesso!");
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Falha ao editar Agenda.");
    }

    public  ResponseEntity<String> deleteAgenda(long id) {
        if (repo.existsById(id)) {
            repo.deleteById(id);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("Agenda  deletada com sucesso!");
        } else {
            //throw new IllegalArgumentException("LinhaAgenda não encontrado com o ID: " + id);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Falha ao deletar Agenda.");
        }

    }
}



