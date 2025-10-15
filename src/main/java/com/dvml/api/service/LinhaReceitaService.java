package com.dvml.api.service;

import com.dvml.api.entity.LinhaReceita;
import com.dvml.api.repository.LinhaReceitaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class LinhaReceitaService {

    @Autowired
    private LinhaReceitaRepository repo;

    public LinhaReceita getLinhaReceitaById(long id) {
        return repo.findById(id).get();
    }

    public List<LinhaReceita> listarTodasLinhasReceitas() {
        return repo.findAllOrderByNomeAsc();
    }

    public ResponseEntity<String> criar(LinhaReceita linhareceita) {
        if (Objects.nonNull(repo.save(linhareceita))) {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("Linha Receita criada com sucesso!");
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Falha ao criar a Linha Receita.");
    }

    public ResponseEntity<String> update(LinhaReceita linhareceita) {
        LinhaReceita linhaReceitaToUpdate = repo.findById(linhareceita.getId()).get();
        linhaReceitaToUpdate.setReceitaId(linhareceita.getReceitaId());
        linhaReceitaToUpdate.setStatus(linhareceita.getStatus());
        linhaReceitaToUpdate.setDosagem(linhareceita.getDosagem());
        linhaReceitaToUpdate.setPosologia(linhareceita.getPosologia());
        linhaReceitaToUpdate.setMedicamento(linhareceita.getMedicamento());
        linhaReceitaToUpdate.setViaAdministracao(linhareceita.getViaAdministracao());
        linhaReceitaToUpdate.setEmpresaId(linhareceita.getEmpresaId()); // ✅ campo adicionado sem alterar lógica

        if (Objects.nonNull(repo.save(linhaReceitaToUpdate))) {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("Linha Receita editada com sucesso!");
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Falha ao editar a Linha Receita.");
    }

    public ResponseEntity<String> deleteLinhaReceita(long id) {
        if (repo.existsById(id)) {
            repo.deleteById(id);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("Linha Receita deletada com sucesso!");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao deletar a Linha Receita.");
        }
    }
}
