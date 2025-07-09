package com.dvml.api.service;

import com.dvml.api.entity.Funcao;
import com.dvml.api.repository.FuncaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class FuncaoService {

    @Autowired
    private FuncaoRepository repo;

    public List<Funcao> listarTodasFuncoes() {
        return repo.findAll();
    }

    public Funcao getFuncaoById(long id) {
        return repo.findById(id).orElse(null);
    }

    public ResponseEntity<Map<String, Object>> criar(Funcao funcao) {
        Funcao novaFuncao = repo.save(funcao);

        Map<String, Object> response = new HashMap<>();
        response.put("mensagem", "Função criada com sucesso.");
        response.put("funcao", novaFuncao);

        return ResponseEntity.status(201).body(response);
    }

    public void update(Funcao funcao) {
        Optional<Funcao> optional = repo.findById(funcao.getId());
        if (optional.isPresent()) {
            Funcao funcaoToUpdate = optional.get();
            funcaoToUpdate.setId(funcao.getId());
            funcaoToUpdate.setDesignacao(funcao.getDesignacao());
            repo.save(funcaoToUpdate);
        }
    }

    public void deleteFuncao(long id) {
        Optional<Funcao> optional = repo.findById(id);
        optional.ifPresent(repo::delete);
    }
}
