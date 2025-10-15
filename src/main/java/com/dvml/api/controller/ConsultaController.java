package com.dvml.api.controller;

import com.dvml.api.dto.ConsultaSimpleDTO;
import com.dvml.api.entity.Consulta;
import com.dvml.api.service.ConsultaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/consulta")
public class ConsultaController {

    @Autowired
    private ConsultaService consultaService;

    // Listar todas as consultas
    @GetMapping("/all")
    public ResponseEntity<List<ConsultaSimpleDTO>> listarTodasConsultas() {
        List<ConsultaSimpleDTO> consultas = consultaService.listarTodos();
        return ResponseEntity.ok(consultas);
    }

    // Buscar consulta por ID
    @GetMapping("/{id}")
    public ResponseEntity<ConsultaSimpleDTO> getConsultaById(@PathVariable Long id) {
        try {
            ConsultaSimpleDTO consulta = consultaService.getConsultaById(id);
            return ResponseEntity.ok(consulta);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // Criar nova consulta
    @PostMapping("/add")
    public ResponseEntity<Consulta> criarConsulta(@RequestBody Consulta consulta) {
        Consulta novaConsulta = consultaService.adicionar(consulta);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaConsulta);
    }

    // Atualizar consulta
    @PutMapping("/{id}")
    public ResponseEntity<Consulta> atualizarConsulta(@PathVariable Long id, @RequestBody Consulta consulta) {
        try {
            consulta.setId(id);
            Consulta atualizada = consultaService.update(consulta);
            return ResponseEntity.ok(atualizada);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // Fechar consulta por inscrição
    @PatchMapping("/fechar/{idInscricao}")
    public ResponseEntity<Void> fecharConsulta(@PathVariable Long idInscricao) {
        try {
            consultaService.updateEstadoCondicaoConsulta(idInscricao);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}