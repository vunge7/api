package com.dvml.api.service;

import com.dvml.api.entity.LinhaGasto;
import com.dvml.api.entity.LinhaRequisicaoExame;
import com.dvml.api.repository.LinhaRequisicaoExameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.Objects;

import java.util.List;

@Service
public class LinhaRequisicaoExameService {
    @Autowired
    private LinhaRequisicaoExameRepository repo;

    public LinhaRequisicaoExame getLinhaRequisicaoById(long id){
        return repo.findById(id).get();
    }
    public List <LinhaRequisicaoExame>listarTodasLinhaRequisicoes() {
        return repo.findAllOrderByNomeAsc();
    }
    public ResponseEntity<String> criar(LinhaRequisicaoExame linhaRequisicaoExame) {
        if(Objects.nonNull(repo.save(linhaRequisicaoExame))) {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("Linha Requisicao criada com sucesso!");
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Falha ao criar o Linha Requisicao.");
    }

    public ResponseEntity<String> update(LinhaRequisicaoExame linhaRequisicaoExame){
        LinhaRequisicaoExame LinhaRequisicaoToUpdate = repo.findById(linhaRequisicaoExame.getId()).get();
        LinhaRequisicaoToUpdate.setRequisicaoExameId(linhaRequisicaoExame.getRequisicaoExameId());
        LinhaRequisicaoToUpdate.setStatus(linhaRequisicaoExame.getStatus());
        LinhaRequisicaoToUpdate.setExame(linhaRequisicaoExame.getExame());
        LinhaRequisicaoToUpdate.setHora(linhaRequisicaoExame.getHora());
        LinhaRequisicaoToUpdate.setEstado(linhaRequisicaoExame.getEstado());
        LinhaRequisicaoToUpdate.setProdutoId(linhaRequisicaoExame.getProdutoId());
        if (Objects.nonNull(repo.save(LinhaRequisicaoToUpdate))){
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("Linha Requisicao editado com sucesso!");
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Falha ao editar a Linha Requisicao.");
    }
    public  ResponseEntity<String> deleteLinhaRequisicao(long id) {
        if (repo.existsById(id)) {
            repo.deleteById(id);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("LinhaRequisicao deletada com sucesso!");
        } else {
            //throw new IllegalArgumentException("Linha não encontrado com o ID: " + id);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao deletar a Linha Requisicao.");
        }
    }
    public List<LinhaRequisicaoExame> listarLinhasPorRequisicaoId(long requisicaoExameId) {
        return repo.findAllByRequisicaoId(requisicaoExameId);
    }

    public List<LinhaRequisicaoExame> listarLinhasPorIncricaoId(long  inscricaoId) {
        return repo.findAllByInscricaoId(inscricaoId);
    }
}
