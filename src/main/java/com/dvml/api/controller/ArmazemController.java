package com.dvml.api.controller;

import com.dvml.api.dto.ArmazemDTO;
import com.dvml.api.service.ArmazemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/armazem")
public class ArmazemController {

    private static final Logger logger = LoggerFactory.getLogger(ArmazemController.class);

    @Autowired
    private ArmazemService service;

    @GetMapping("/all")
    public List<ArmazemDTO> getAllArmazem() {
        logger.info("Requisição para listar todos os armazéns");
        List<ArmazemDTO> armazens = service.listarTodasArmazem();
        logger.debug("Armazéns retornados: {}", armazens);
        return armazens;
    }

    @GetMapping("/{id}")
    public ArmazemDTO getArmazemById(@PathVariable Long id) {
        logger.info("Requisição para buscar armazém com ID: {}", id);
        ArmazemDTO armazem = service.getArmazemById(id);
        logger.debug("Armazém retornado: {}", armazem);
        return armazem;
    }

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public ArmazemDTO adicionar(@RequestBody @Valid ArmazemDTO armazemDTO) {
        logger.info("Requisição para adicionar armazém: {}", armazemDTO.getDesignacao());
        if (armazemDTO.getFilialId() == null) {
            logger.error("Filial ID é obrigatório para armazém: {}", armazemDTO.getDesignacao());
            throw new IllegalArgumentException("Filial ID é obrigatório");
        }
        ArmazemDTO created = service.criar(armazemDTO);
        logger.debug("Armazém criado: {}", created);
        return created;
    }

    @PutMapping("/edit")
    @ResponseStatus(HttpStatus.OK)
    public ArmazemDTO updateArmazem(@RequestBody @Valid ArmazemDTO armazemDTO) {
        logger.info("Requisição para atualizar armazém com ID: {}", armazemDTO.getId());
        if (armazemDTO.getFilialId() == null) {
            logger.error("Filial ID é obrigatório para armazém ID: {}", armazemDTO.getId());
            throw new IllegalArgumentException("Filial ID é obrigatório");
        }
        ArmazemDTO updated = service.update(armazemDTO);
        logger.debug("Armazém atualizado: {}", updated);
        return updated;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteArmazem(@PathVariable Long id) {
        logger.info("Requisição para deletar armazém com ID: {}", id);
        service.deleteArmazem(id);
        logger.debug("Armazém com ID {} deletado com sucesso", id);
    }
}