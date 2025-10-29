package com.dvml.api.service;

import com.dvml.api.entity.Seguradora;
import com.dvml.api.repository.SeguradoraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class SeguradoraService {

    @Autowired
    private SeguradoraRepository repo;

    // Listar todas seguradoras
    public List<Seguradora> listarTodasSeguradoras() {
        return repo.findAll();
    }

    // Buscar seguradora por ID
    public Seguradora getSeguradoraById(long id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Seguradora não encontrada com ID: " + id));
    }

    // Criar nova seguradora
    public Seguradora criar(Seguradora seguradora) {
        // empresaId será salvo se preenchido
        return repo.save(seguradora);
    }

    // Atualizar seguradora existente
    public ResponseEntity<String> atualizar(long id, Seguradora novaSeguradora) {
        Optional<Seguradora> optionalSeguradora = repo.findById(id);

        if (!optionalSeguradora.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Seguradora não encontrada com ID: " + id);
        }

        Seguradora seguradoraExistente = optionalSeguradora.get();
        seguradoraExistente.setNome(novaSeguradora.getNome());
        seguradoraExistente.setTelefone(novaSeguradora.getTelefone());
        seguradoraExistente.setEndereco(novaSeguradora.getEndereco());
        seguradoraExistente.setNif(novaSeguradora.getNif());
        seguradoraExistente.setStatus(novaSeguradora.getStatus());
        seguradoraExistente.setEmpresaId(novaSeguradora.getEmpresaId()); // <--- empresaId adicionado

        if (Objects.nonNull(repo.save(seguradoraExistente))) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body("Seguradora atualizada com sucesso!");
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Falha ao atualizar a seguradora.");
    }

    // Deletar seguradora
    public ResponseEntity<String> deletar(long id) {
        if (repo.existsById(id)) {
            repo.deleteById(id);
            return ResponseEntity.ok("Seguradora deletada com sucesso!");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Seguradora não encontrada com ID: " + id);
        }
    }
}
