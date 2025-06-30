package com.dvml.api.service;
import com.dvml.api.entity.UnidadeMedidas;
import com.dvml.api.repository.UnidadeMedidaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
@Service
public class UnidadeMedidaService {


    @Autowired
    private UnidadeMedidaRepository repo;


    public List<UnidadeMedidas> listarTodasUnidadeMedidas() {
        return repo.findAll();
    }

    public UnidadeMedidas getUnidadeMedidasById(long id) {
        return repo.findById(id).get();
    }

    public UnidadeMedidas criar(UnidadeMedidas unidadeMedidas) {
        return repo.save(unidadeMedidas);
    }


    public ResponseEntity<String> update(UnidadeMedidas unidadeMedidas){
         UnidadeMedidas unidadeMedidasToUpdate = repo.findById(unidadeMedidas.getId()).get();
         unidadeMedidasToUpdate.setId(unidadeMedidas.getId());
        unidadeMedidasToUpdate.setDescricao(unidadeMedidas.getDescricao());
        unidadeMedidasToUpdate.setAbrevicao(unidadeMedidas.getAbrevicao());

         if(Objects.nonNull(repo.save(unidadeMedidasToUpdate))) {
             return ResponseEntity.status(HttpStatus.CREATED)
                     .body("Unidade de Medida editada com sucesso!");
         }
         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                 .body("Falha ao editar Unidade de Medida.");
     }
    public  ResponseEntity<String> deleteUnidadeMedidas(long id) {
        if (repo.existsById(id)) {
            repo.deleteById(id);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("Unidade de Medida deletada com sucesso!");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Falha ao deletar Unidade de Medida.");
        }

    }
}



