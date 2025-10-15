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

    public LinhaRequisicaoExame getLinhaRequisicaoById(long id) {
        return repo.findById(id).orElse(null);
    }

    public List<LinhaRequisicaoExame> listarTodasLinhaRequisicoes() {
        return repo.findAllOrderByNomeAsc();
    }

    public ResponseEntity<String> criar(LinhaRequisicaoExame linhaRequisicaoExame) {
        linhaRequisicaoExame.setHora(LocalDateTime.now());
        if (Objects.nonNull(repo.save(linhaRequisicaoExame))) {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("Linha Requisição criada com sucesso!");
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Falha ao criar a Linha Requisição.");
    }

    public ResponseEntity<String> update(LinhaRequisicaoExame linhaRequisicaoExame) {
        Optional<LinhaRequisicaoExame> opt = repo.findById(linhaRequisicaoExame.getId());
        if (opt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Linha Requisição não encontrada!");
        }

        LinhaRequisicaoExame linhaRequisicaoToUpdate = opt.get();
        linhaRequisicaoToUpdate.setRequisicaoExameId(linhaRequisicaoExame.getRequisicaoExameId());
        linhaRequisicaoToUpdate.setStatus(linhaRequisicaoExame.getStatus());
        linhaRequisicaoToUpdate.setExame(linhaRequisicaoExame.getExame());
        linhaRequisicaoToUpdate.setHora(linhaRequisicaoExame.getHora());
        linhaRequisicaoToUpdate.setEstado(linhaRequisicaoExame.getEstado());
        linhaRequisicaoToUpdate.setProdutoId(linhaRequisicaoExame.getProdutoId());
        linhaRequisicaoToUpdate.setFinalizado(linhaRequisicaoExame.getFinalizado());
        linhaRequisicaoToUpdate.setEmpresaId(linhaRequisicaoExame.getEmpresaId()); // ✅ campo adicionado

        if (Objects.nonNull(repo.save(linhaRequisicaoToUpdate))) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body("Linha Requisição editada com sucesso!");
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Falha ao editar a Linha Requisição.");
    }

    public ResponseEntity<String> deleteLinhaRequisicao(long id) {
        if (repo.existsById(id)) {
            repo.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK)
                    .body("Linha Requisição deletada com sucesso!");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Erro ao deletar a Linha Requisição.");
        }
    }

    public List<LinhaRequisicaoExame> listarLinhasPorRequisicaoId(long requisicaoExameId) {
        return repo.findAllByRequisicaoId(requisicaoExameId);
    }
}
