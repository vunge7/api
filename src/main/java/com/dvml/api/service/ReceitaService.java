package com.dvml.api.service;

import com.dvml.api.entity.Produto;
import com.dvml.api.entity.Receita;
import com.dvml.api.repository.ReceitaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.Objects;

import java.util.List;

@Service
public class ReceitaService {
    @Autowired
    private ReceitaRepository repo;

    public Receita getReceitaById(long id){
        return repo.findById(id).get();
    }
    public List <Receita>listarTodasReceitas() {
        return repo.findAllOrderByNomeAsc();
    }
    public Receita criar(Receita receita) {

        return repo.save(receita);
        /*
        if(Objects.nonNull(repo.save(receita))) {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("Receita criado com sucesso!");
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Falha ao criar o Receita.");

         */
    }

    public ResponseEntity<String> update(Receita receita){
        Receita ReceitaToUpdate = repo.findById(receita.getId()).get();
        ReceitaToUpdate.setData(receita.getData());
        ReceitaToUpdate.setStatus(receita.getStatus());
        ReceitaToUpdate.setUsuarioId(receita.getUsuarioId());
        ReceitaToUpdate.setInscricaoId(receita.getInscricaoId());
        if (Objects.nonNull(repo.save(receita))){
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("Receita editado com sucesso!");
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Falha ao editar o Receita.");
    }
    public  ResponseEntity<String> deleteReceita(long id) {
        if (repo.existsById(id)) {
            repo.deleteById(id);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("Receita deletada com sucesso!");
        } else {
            //throw new IllegalArgumentException("Linha não encontrado com o ID: " + id);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao deletar a Receita.");
        }
}}
