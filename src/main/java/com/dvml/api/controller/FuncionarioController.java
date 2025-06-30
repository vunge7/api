package com.dvml.api.controller;

import com.dvml.api.dto.FuncionarioDTO;
import com.dvml.api.dto.LinhaSubsidioDTO;
import com.dvml.api.service.FuncionarioService;
import com.dvml.api.service.LinhaSubsidioService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
public class FuncionarioController {

    private static final Logger LOGGER = LoggerFactory.getLogger(FuncionarioController.class);

    @Autowired
    private FuncionarioService funcionarioService;

    @Autowired
    private LinhaSubsidioService linhaSubsidioService;

    @PostMapping("funcionario/add")
    public ResponseEntity<FuncionarioDTO> create(@Valid @RequestBody FuncionarioDTO funcionarioDTO) {
        LOGGER.info("Criando funcionário com pessoaId: {}", funcionarioDTO.getPessoaId());
        FuncionarioDTO created = funcionarioService.create(funcionarioDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("funcionario/{id}")
    public ResponseEntity<FuncionarioDTO> findById(@PathVariable("id") Long id) {
        LOGGER.info("Buscando funcionário ID: {}", id);
        FuncionarioDTO funcionarioDTO = funcionarioService.findById(id);
        return ResponseEntity.ok(funcionarioDTO);
    }

    @GetMapping("funcionario/all")
    public ResponseEntity<List<FuncionarioDTO>> findAll() {
        LOGGER.info("Buscando todos os funcionários");
        List<FuncionarioDTO> funcionarios = funcionarioService.findAll();
        return ResponseEntity.ok(funcionarios);
    }

    @PutMapping("funcionario/{id}")
    public ResponseEntity<FuncionarioDTO> update(@PathVariable("id") Long id, @Valid @RequestBody FuncionarioDTO funcionarioDTO) {
        LOGGER.info("Atualizando funcionário ID: {}", id);
        FuncionarioDTO updated = funcionarioService.update(id, funcionarioDTO);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("funcionario/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        LOGGER.info("Deletando funcionário ID: {}", id);
        funcionarioService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("funcionario/{id}/subsidios")
    public ResponseEntity<List<LinhaSubsidioDTO>> getSubsidios(@PathVariable("id") Long id) {
        LOGGER.info("Buscando subsídios do funcionário ID: {}", id);
        List<LinhaSubsidioDTO> subsidios = linhaSubsidioService.findByFuncionarioId(id);
        return ResponseEntity.ok(subsidios);
    }

    @GetMapping("funcionario/pessoa/{pessoaId}")
    public ResponseEntity<Map<String, Boolean>> checkFuncionarioByPessoaId(@PathVariable("pessoaId") Long pessoaId) {
        LOGGER.info("Verificando funcionário para pessoa ID: {}", pessoaId);
        boolean exists = funcionarioService.existsByPessoaId(pessoaId);
        return ResponseEntity.ok(Collections.singletonMap("exists", exists));
    }
}