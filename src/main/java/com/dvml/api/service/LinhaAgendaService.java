package com.dvml.api.service;

import com.dvml.api.entity.LinhaAgenda;
import com.dvml.api.repository.LinhaAgendaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Objects;

@Service
public class LinhaAgendaService {
    @Autowired
    private LinhaAgendaRepository repo;
    public List<LinhaAgenda> listarTodasLinhasAgenda() {

        return repo.findAllActiveOrderByDataRealizacaoAsc();
    }
    public LinhaAgenda getLinhaAgendaById(long id) {
        return repo.findById(id).get();
    }

    public LinhaAgenda criar(LinhaAgenda linhaagenda) {
        return repo.save(linhaagenda);
    }

    public ResponseEntity<String> update(LinhaAgenda linhaagenda){
        LinhaAgenda linhaagendaToUpdate = repo.findById(linhaagenda.getId()).get();
        linhaagendaToUpdate.setId(linhaagenda.getId());
        linhaagendaToUpdate.setAgendaId(linhaagenda.getAgendaId());
        linhaagendaToUpdate.setStatus(linhaagenda.getStatus());
        linhaagendaToUpdate.setFuncionarioId(linhaagenda.getFuncionarioId());
        linhaagendaToUpdate.setConsultaId((linhaagenda.getConsultaId()));
        linhaagendaToUpdate.setDataRealizacao(linhaagenda.getDataRealizacao());


        if(Objects.nonNull(repo.save(linhaagendaToUpdate))) {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("LinhaAgenda editada com sucesso!");
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Falha ao editar LinhaAgenda.");
    }
    public  ResponseEntity<String> deleteLinhaAgenda(long id) {
        if (repo.existsById(id)) {
            repo.deleteById(id);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("LinhaAgenta  deletada com sucesso!");
        } else {
            //throw new IllegalArgumentException("LinhaAgenda não encontrado com o ID: " + id);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Falha ao deletar LinhaAgenda.");
        }

    }

}
