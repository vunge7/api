package com.dvml.api.controller;

import com.dvml.api.dto.AgendaDTO;
import com.dvml.api.dto.MedicoDTO;
import com.dvml.api.service.MedicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/medicos")
public class MedicoController {

    @Autowired
    private MedicoService medicoService;

    @GetMapping("/f/{id}")
    public ResponseEntity<?> getMedicoById(@PathVariable long id) {
        return medicoService.getMedicoById(id);
    }

    @GetMapping("/a/{medicoId}")
    public ResponseEntity<List<AgendaDTO>> getAgendasByMedico(@PathVariable Long medicoId) {
        List<AgendaDTO> agendas = medicoService.getAgendasByMedico(medicoId);
        return ResponseEntity.ok(agendas);
    }

    @GetMapping("/all")
    public ResponseEntity<List<MedicoDTO>> getAllMedicos() {
        List<MedicoDTO> medicos = medicoService.getAllMedicos();
        return ResponseEntity.ok(medicos);
    }
}