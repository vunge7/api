package com.dvml.api.controller;


import com.dvml.api.dto.RequisicaoExameDTO;
import com.dvml.api.entity.RequisicaoExame;
import com.dvml.api.service.RequisicaoExameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class RequisicaoExameController {
    @Autowired
    private RequisicaoExameService service;


    @GetMapping("requisicaoexame/all")
    public List<RequisicaoExame> getAllReceita() {
        return service.listarTodasRequisicoes();
    }

    @GetMapping("requisicaoexame/all/composto")
    public List<RequisicaoExameDTO> getAllReceitaComposto() {
        return service.listarTodasRequisicoesComposto();
    }

    @GetMapping("requisicaoexame/{id}")
    public RequisicaoExame getAllRequisicaoById(@PathVariable long id) {
        return service.getRequisicaoById(id);
    }

    @PostMapping("requisicaoexame/add")
    @ResponseStatus(code = HttpStatus.CREATED)
    public RequisicaoExame adicionar(@RequestBody @Valid RequisicaoExame requisicaoExame){
        return service.criar(requisicaoExame);
    }
    @PutMapping("requisicaoexame/edit")
    @ResponseStatus(code = HttpStatus.CREATED)
    public void updateRequisicao(@RequestBody @Valid RequisicaoExame requisicaoExame){
        service.update(requisicaoExame);}

    @DeleteMapping("requisicaoexame/{id}")
    public void deleteequisicao(@PathVariable long id) {
        if (service.getRequisicaoById(id) != null) {
            service.deleteRequisicao(id);
        } else {
            System.out.println("ERRO...");
        }
    }
}
