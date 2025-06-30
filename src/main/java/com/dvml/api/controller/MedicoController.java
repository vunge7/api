package com.dvml.api.controller;

import com.dvml.api.dto.AgendaDTO;
import com.dvml.api.service.AgendaService;
import com.dvml.api.service.MedicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController

public class MedicoController {

    @Autowired
    private MedicoService medicoService;

    // Rota para buscar um Usuário pelo ID
    @GetMapping("medicos/f/{id}")
    public ResponseEntity<?> getMedicoById(@PathVariable long id) {
        return medicoService.getMedicoById(id);
    }

    @GetMapping("medicos/a/{medicoId}")
    public ResponseEntity<List<AgendaDTO>> getAgendasByMedico(@PathVariable Long medicoId) {
        List<AgendaDTO> agendas = medicoService.getAgendasByMedico(medicoId);
        return ResponseEntity.ok(agendas);
    }
}
