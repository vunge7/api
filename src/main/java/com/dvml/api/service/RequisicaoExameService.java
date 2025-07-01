package com.dvml.api.service;

import com.dvml.api.entity.RequisicaoExame;
import com.dvml.api.repository.RequisicaoExameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Objects;

import java.util.List;

@Service
public class RequisicaoExameService {
    @Autowired
    private RequisicaoExameRepository repo;

    public RequisicaoExame getRequisicaoById(long id) {
        return repo.findById(id).get();
    }

    public List<RequisicaoExame> listarTodasRequisicoes() {
        return repo.findAllOrderByNomeAsc();
    }

    public RequisicaoExame criar(RequisicaoExame requisicaoExame) {

        requisicaoExame.setDataRequisicao(new Date());

        return repo.save(requisicaoExame);
       /*
        if(Objects.nonNull(repo.save(requisicaoExame))) {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("Requisicao criada com sucesso!");
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Falha ao criar o Requisicao.");

        */
    }

    public ResponseEntity<String> update(RequisicaoExame requisicaoExame) {
        RequisicaoExame RequisicaoToUpdate = repo.findById(requisicaoExame.getId()).get();
        RequisicaoToUpdate.setDataRequisicao(requisicaoExame.getDataRequisicao());
        RequisicaoToUpdate.setStatus(requisicaoExame.getStatus());
        RequisicaoToUpdate.setUsuarioId(requisicaoExame.getUsuarioId());
        if (Objects.nonNull(repo.save(requisicaoExame))) {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("Requisicao editado com sucesso!");
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Falha ao editar o Requisicao.");
    }

    public ResponseEntity<String> deleteRequisicao(long id) {
        if (repo.existsById(id)) {
            repo.deleteById(id);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("Requisicao deletada com sucesso!");
        } else {
            //throw new IllegalArgumentException("Linha não encontrado com o ID: " + id);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao deletar a Requisicao.");
        }
    }
}
