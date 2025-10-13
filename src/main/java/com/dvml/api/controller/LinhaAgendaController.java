package com.dvml.api.controller;

import com.dvml.api.entity.LinhaAgenda;
import com.dvml.api.service.LinhaAgendaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid; // substitui javax.validation se usas Spring Boot 3+
import java.util.List;

@RestController
public class LinhaAgendaController {

    @Autowired
    private LinhaAgendaService service;

    // 🔹 Listar todas as linhas de agenda
    @GetMapping("linhaagenda/all")
    public List<LinhaAgenda> listarTodas() {
        return service.listarTodasLinhasAgenda();
    }

    // 🔹 Buscar uma linha específica
    @GetMapping("linhaagenda/{id}")
    public ResponseEntity<LinhaAgenda> buscarPorId(@PathVariable long id) {
        LinhaAgenda linha = service.getLinhaAgendaById(id);
        if (linha != null) {
            return ResponseEntity.ok(linha);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    // 🔹 Criar nova linha de agenda (com produtoId incluído no JSON)
    @PostMapping("linhaagenda/add")
    @ResponseStatus(HttpStatus.CREATED)
    public LinhaAgenda adicionar(@RequestBody @Valid LinhaAgenda linhaAgenda) {
        return service.criar(linhaAgenda);
    }

    // 🔹 Atualizar linha existente
    @PutMapping("linhaagenda/edit")
    public ResponseEntity<String> atualizar(@RequestBody @Valid LinhaAgenda linhaAgenda) {
        return service.update(linhaAgenda);
    }

    // 🔹 Deletar uma linha de agenda
    @DeleteMapping("linhaagenda/{id}")
    public ResponseEntity<String> deletar(@PathVariable long id) {
        if (service.getLinhaAgendaById(id) != null) {
            return service.deleteLinhaAgenda(id);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("LinhaAgenda não encontrada para exclusão.");
    }
}
