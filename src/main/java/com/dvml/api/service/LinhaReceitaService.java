package com.dvml.api.service;

import com.dvml.api.entity.LinhaReceita;
import com.dvml.api.entity.Produto;
import com.dvml.api.entity.Receita;
import com.dvml.api.repository.LinhaReceitaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.Objects;

import java.util.List;

@Service
public class LinhaReceitaService {
    @Autowired
    private LinhaReceitaRepository repo;

    public LinhaReceita getLinhaReceitaById(long id){
        return repo.findById(id).get();
    }
    public List <LinhaReceita>listarTodasLinhasReceitas() {
        return repo.findAllOrderByNomeAsc();
    }
    public ResponseEntity<String> criar(LinhaReceita linhareceita) {
        if(Objects.nonNull(repo.save(linhareceita))) {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("Linha Receita criado com sucesso!");
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Falha ao criar o Linha Receita.");
    }

    public ResponseEntity<String> update(LinhaReceita linhareceita){
        LinhaReceita linhaReceitaToUpdate = repo.findById(linhareceita.getId()).get();
        linhaReceitaToUpdate.setReceitaId(linhareceita.getReceitaId());
        linhaReceitaToUpdate.setStatus(linhareceita.getStatus());
        linhaReceitaToUpdate.setDosagem(linhareceita.getDosagem());
        linhaReceitaToUpdate.setPosologia(linhareceita.getPosologia());
        linhaReceitaToUpdate.setMedicamento(linhareceita.getMedicamento());
        linhaReceitaToUpdate.setViaAdministracao(linhareceita.getViaAdministracao());
        if (Objects.nonNull(repo.save(linhareceita))){
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("Linha Receita editado com sucesso!");
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Falha ao editar o Linha Receita.");
    }
    public  ResponseEntity<String> deleteLinhaReceita(long id) {
        if (repo.existsById(id)) {
            repo.deleteById(id);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("LinhaReceita deletada com sucesso!");
        } else {
            //throw new IllegalArgumentException("LinhaReceita não encontrado com o ID: " + id);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao deletar a LinhaReceita.");
        }
    }
}
