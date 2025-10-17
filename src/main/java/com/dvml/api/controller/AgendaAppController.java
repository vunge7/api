package com.dvml.api.controller;

import com.dvml.api.dto.AgendaAppDTO;
import com.dvml.api.service.AgendaAppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/agenda-app")
public class AgendaAppController {

    @Autowired
    private AgendaAppService agendaAppService;

    // Criar agendamento via app
    @PostMapping("/add")
    public ResponseEntity<?> criarAgendaViaApp(@RequestBody AgendaAppDTO dto) {
        try {
            return agendaAppService.criarAgendaViaApp(dto);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Erro interno ao criar agendamento: " + e.getMessage());
        }
    }
}