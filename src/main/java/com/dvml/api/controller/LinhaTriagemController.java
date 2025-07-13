package com.dvml.api.controller;

import com.dvml.api.dto.LinhaTriagemDTO;
import com.dvml.api.dto.SinalVitalDTO;
import com.dvml.api.entity.LinhaTriagem;
import com.dvml.api.service.LinhaTriagemService;
import com.dvml.api.util.Campo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "linhatriagem", produces = "application/json")
public class LinhaTriagemController {

    @Autowired
    private LinhaTriagemService service;

    @GetMapping("/all")
    public List<LinhaTriagemDTO> getAllLinhas() {
        return service.listarTodos();
    }

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public LinhaTriagemDTO criarTriagem(@RequestBody LinhaTriagem linha) {
        LinhaTriagem t = service.salvar(linha);
        return service.convertEntityToDto(t);
    }

    @PostMapping("/add/all")
    @ResponseStatus(HttpStatus.CREATED)
    public List<LinhaTriagemDTO> criarVariasTriagens(@RequestBody List<LinhaTriagem> lista) {
        return lista.stream()
                .map(service::salvar)
                .map(service::convertEntityToDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{pacienteId}/{campo}")
    public List<SinalVitalDTO> getSinaisVitaisPorPaciente(
            @PathVariable Long pacienteId,
            @PathVariable Campo campo) {
        return service.obterSinalVitalPorPaciente(pacienteId, campo);
    }

    @GetMapping("/native/{pacienteId}/sinais")
    public List<SinalVitalDTO> getSinaisVitais(@PathVariable Long pacienteId) {
        return service.obterTodosSinaisPorPacienteNativo(pacienteId);
    }

    // 🔹 NOVO: Endpoint agrupado por tipo de sinal vital (campo)
    @GetMapping("/grouped/{pacienteId}")
    public Map<String, List<SinalVitalDTO>> getSinaisAgrupados(@PathVariable Long pacienteId) {
        return service.agruparSinaisPorCampo(pacienteId);
    }
    public ResponseEntity<LinhaTriagem> atualizar(
            @PathVariable Long id,
            @RequestBody LinhaTriagem linha) {
        return ResponseEntity.ok(service.atualizar(id, linha));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
