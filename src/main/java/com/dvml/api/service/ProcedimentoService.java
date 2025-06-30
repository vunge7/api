package com.dvml.api.service;


import com.dvml.api.entity.Pessoa;
import com.dvml.api.entity.Procedimento;
import com.dvml.api.repository.PessoaRepository;
import com.dvml.api.repository.ProcedimentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class ProcedimentoService {

    @Autowired
    private ProcedimentoRepository repo;

    @Autowired

    private ProcedimentoRepository procedimentoRepository;

    public List<Procedimento> listarTodosProcedimentos() {
        return procedimentoRepository.findAllOrderByNomeAsc();
    }

    public ResponseEntity<Procedimento> criar(Procedimento procedimento) {
        Procedimento procedimentoSalvo = repo.save(procedimento);
        if (Objects.nonNull(procedimentoSalvo)) {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(procedimentoSalvo);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(null);
    }

    public ResponseEntity<String> update(Procedimento procedimento){
        Procedimento procedimentoToUpdate = repo.findById(procedimento.getId()).get();
        procedimentoToUpdate.setId(procedimento.getId());
        procedimentoToUpdate.setUsuarioId(procedimento.getUsuarioId());
        procedimentoToUpdate.setDataCricao(procedimento.getDataCricao());
        procedimentoToUpdate.setInscricaoId(procedimento.getInscricaoId());
        procedimentoToUpdate.setStatus(procedimento.getStatus());
        procedimentoToUpdate.setDataActualizacao(procedimento.getDataActualizacao());


        if(Objects.nonNull(repo.save(procedimentoToUpdate))) {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("Procedimento editado com sucesso!");
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Falha ao editar Procedimento.");
    }
    public  ResponseEntity<String> deleteProcedimento(long id) {
        if (repo.existsById(id)) {
            repo.deleteById(id);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("Procedimento  deletada com sucesso!");
        } else {
            //throw new IllegalArgumentException("Pessoa não encontrado com o ID: " + id);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Falha ao deletar procedimento.");
        }

    }


}



