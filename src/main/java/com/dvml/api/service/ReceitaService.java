package com.dvml.api.service;

import com.dvml.api.entity.Receita;
import com.dvml.api.repository.ReceitaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Objects;

@Service
public class ReceitaService {

    @Autowired
    private ReceitaRepository repo;

    public Receita getReceitaById(long id){
        return repo.findById(id).orElseThrow(() -> new RuntimeException("Receita não encontrada"));
    }

    public List<Receita> listarTodasReceitas() {
        return repo.findAllOrderByNomeAsc();
    }

    public Receita criar(Receita receita) {
        // ✅ Setando empresaId antes de salvar (vem do objeto receita)
        return repo.save(receita);
    }

    public ResponseEntity<String> update(Receita receita){
        Receita receitaToUpdate = repo.findById(receita.getId())
                .orElseThrow(() -> new RuntimeException("Receita não encontrada"));

        receitaToUpdate.setData(receita.getData());
        receitaToUpdate.setStatus(receita.getStatus());
        receitaToUpdate.setUsuarioId(receita.getUsuarioId());
        receitaToUpdate.setInscricaoId(receita.getInscricaoId());

        // ✅ Atualizando empresaId
        receitaToUpdate.setEmpresaId(receita.getEmpresaId());

        if (Objects.nonNull(repo.save(receitaToUpdate))){
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("Receita editada com sucesso!");
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Falha ao editar a Receita.");
    }

    public ResponseEntity<String> deleteReceita(long id) {
        if (repo.existsById(id)) {
            repo.deleteById(id);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("Receita deletada com sucesso!");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao deletar a Receita.");
        }
    }
}
