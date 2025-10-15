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

    /**
     * Lista todas as funções
     */
    public List<Funcao> listarTodasFuncoes() {
        return repo.findAll();
    }

    /**
     * Busca função por ID
     */
    public Funcao getFuncaoById(long id) {
        return repo.findById(id).orElse(null);
    }

    /**
     * Cria uma nova função
     */
    public ResponseEntity<Map<String, Object>> criar(Funcao funcao) {
        Funcao novaFuncao = repo.save(funcao);

        Map<String, Object> response = new HashMap<>();
        response.put("mensagem", "Função criada com sucesso.");
        response.put("funcao", novaFuncao);

        return ResponseEntity.status(201).body(response);
    }

    /**
     * Atualiza função existente
     */
    public ResponseEntity<Map<String, Object>> update(Funcao funcao) {
        Optional<Funcao> optional = repo.findById(funcao.getId());
        if (optional.isPresent()) {
            Funcao funcaoToUpdate = optional.get();
            funcaoToUpdate.setDesignacao(funcao.getDesignacao());
            funcaoToUpdate.setEmpresaId(funcao.getEmpresaId()); // ADICIONADO
            repo.save(funcaoToUpdate);

            Map<String, Object> response = new HashMap<>();
            response.put("mensagem", "Função atualizada com sucesso.");
            response.put("funcao", funcaoToUpdate);
            return ResponseEntity.ok(response);
        } else {
            Map<String, Object> response = new HashMap<>();
            response.put("mensagem", "Função não encontrada.");
            return ResponseEntity.status(404).body(response);
        }
    }

    /**
     * Deleta função por ID
     */
    public ResponseEntity<Map<String, String>> deleteFuncao(long id) {
        Optional<Funcao> optional = repo.findById(id);
        Map<String, String> response = new HashMap<>();
        if (optional.isPresent()) {
            repo.delete(optional.get());
            response.put("mensagem", "Função deletada com sucesso.");
            return ResponseEntity.ok(response);
        } else {
            response.put("mensagem", "Função não encontrada.");
            return ResponseEntity.status(404).body(response);
        }
    }
}
