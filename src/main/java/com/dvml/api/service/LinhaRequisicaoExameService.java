package com.dvml.api.service;

import com.dvml.api.entity.LinhaRequisicaoExame;
import com.dvml.api.repository.LinhaRequisicaoExameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class LinhaRequisicaoExameService {
    @Autowired
    private LinhaRequisicaoExameRepository repo;

    public LinhaRequisicaoExame getLinhaRequisicaoById(long id){
        return repo.findById(id).orElse(null);
    }

    public List<LinhaRequisicaoExame> listarTodasLinhaRequisicoes() {
        return repo.findAllOrderByNomeAsc();
    }

    public ResponseEntity<String> criar(LinhaRequisicaoExame linhaRequisicaoExame) {
        linhaRequisicaoExame.setHora(LocalDateTime.now());
        if(Objects.nonNull(repo.save(linhaRequisicaoExame))) {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("Linha Requisicao criada com sucesso!");
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Falha ao criar o Linha Requisicao.");
    }

    public ResponseEntity<String> update(LinhaRequisicaoExame linhaRequisicaoExame){
        Optional<LinhaRequisicaoExame> opt = repo.findById(linhaRequisicaoExame.getId());
        if (opt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Linha Requisicao não encontrada!");
        }
        LinhaRequisicaoExame LinhaRequisicaoToUpdate = opt.get();
        LinhaRequisicaoToUpdate.setRequisicaoExameId(linhaRequisicaoExame.getRequisicaoExameId());
        LinhaRequisicaoToUpdate.setStatus(linhaRequisicaoExame.getStatus());
        LinhaRequisicaoToUpdate.setExame(linhaRequisicaoExame.getExame());
        LinhaRequisicaoToUpdate.setHora(linhaRequisicaoExame.getHora());
        LinhaRequisicaoToUpdate.setEstado(linhaRequisicaoExame.getEstado());
        LinhaRequisicaoToUpdate.setProdutoId(linhaRequisicaoExame.getProdutoId());
        // CORREÇÃO: Atualizar o campo finalizado!
        LinhaRequisicaoToUpdate.setFinalizado(linhaRequisicaoExame.getFinalizado());

        if (Objects.nonNull(repo.save(LinhaRequisicaoToUpdate))){
            return ResponseEntity.status(HttpStatus.OK)
                    .body("Linha Requisicao editada com sucesso!");
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Falha ao editar a Linha Requisicao.");
    }

    public ResponseEntity<String> deleteLinhaRequisicao(long id) {
        if (repo.existsById(id)) {
            repo.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK)
                    .body("LinhaRequisicao deletada com sucesso!");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Erro ao deletar a Linha Requisicao.");
        }
    }

    public List<LinhaRequisicaoExame> listarLinhasPorRequisicaoId(long requisicaoExameId) {
        return repo.findAllByRequisicaoId(requisicaoExameId);
    }
}