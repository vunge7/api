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
@RequestMapping("/linhatriagem")
public class LinhaTriagemController {

    @Autowired
    private LinhaTriagemService service;

    // 🔹 Listar todas as linhas de triagem
    @GetMapping("/all")
    public List<LinhaTriagemDTO> getAllLinhas() {
        return service.listarTodos();
    }

    // 🔹 Criar uma linha de triagem
    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public LinhaTriagemDTO criarTriagem(@RequestBody LinhaTriagem linha) {
        LinhaTriagem t = service.salvar(linha);
        return service.convertEntityToDto(t);
    }

    // 🔹 Criar várias linhas de triagem
    @PostMapping("/add/all")
    @ResponseStatus(HttpStatus.CREATED)
    public List<LinhaTriagemDTO> criarVariasTriagens(@RequestBody List<LinhaTriagem> lista) {
        return lista.stream()
                .map(service::salvar)
                .map(service::convertEntityToDto)
                .collect(Collectors.toList());
    }

    // 🔹 Obter sinais vitais de um paciente por campo
    @GetMapping("/{pacienteId}/{campo}")
    public List<SinalVitalDTO> getSinaisVitaisPorPaciente(
            @PathVariable Long pacienteId,
            @PathVariable Campo campo) {
        return service.obterSinalVitalPorPaciente(pacienteId, campo);
    }

    // 🔹 Obter todos os sinais vitais de um paciente (sem agrupar)
    @GetMapping("/native/{pacienteId}/sinais")
    public List<SinalVitalDTO> getTodosSinais(@PathVariable Long pacienteId) {
        return service.agruparSinaisPorCampo(pacienteId)
                .values()
                .stream()
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }

    // 🔹 Obter sinais vitais agrupados por campo
    @GetMapping("/grouped/{pacienteId}")
    public Map<Campo, List<SinalVitalDTO>> getSinaisAgrupados(@PathVariable Long pacienteId) {
        return service.agruparSinaisPorCampo(pacienteId);
    }

    // 🔹 Atualizar linha de triagem
    @PutMapping("/{id}")
    public ResponseEntity<LinhaTriagem> atualizar(
            @PathVariable Long id,
            @RequestBody LinhaTriagem linha) {
        return ResponseEntity.ok(service.atualizar(id, linha));
    }

    // 🔹 Deletar linha de triagem
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
