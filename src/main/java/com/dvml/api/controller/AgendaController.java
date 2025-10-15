package com.dvml.api.controller;

import com.dvml.api.entity.Agenda;
import com.dvml.api.service.AgendaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/agenda")
public class AgendaController {

    @Autowired
    private AgendaService agendaService;

    // Listar todas as agendas
    @GetMapping("/all")
    public ResponseEntity<List<Agenda>> listarTodasAgenda() {
        List<Agenda> agendas = agendaService.listarTodasAgenda();
        return ResponseEntity.ok(agendas);
    }

    // Buscar agenda por ID
    @GetMapping("/{id}")
    public ResponseEntity<Agenda> getAgendaById(@PathVariable Long id) {
        try {
            Agenda agenda = agendaService.getAgendaById(id);
            return ResponseEntity.ok(agenda);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // Criar nova agenda
    @PostMapping("/add")
    public ResponseEntity<Agenda> criarAgenda(@RequestBody Agenda agenda) {
        Agenda novaAgenda = agendaService.criar(agenda);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaAgenda);
    }

    // Atualizar agenda
    @PutMapping("/{id}")
    public ResponseEntity<String> atualizarAgenda(@PathVariable Long id, @RequestBody Agenda agenda) {
        agenda.setId(id);
        ResponseEntity<String> response = agendaService.update(agenda);
        return response;
    }

    // Deletar agenda
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletarAgenda(@PathVariable Long id) {
        ResponseEntity<String> response = agendaService.deleteAgenda(id);
        return response;
    }
}