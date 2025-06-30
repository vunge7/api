package com.dvml.api.controller;


import com.dvml.api.entity.LinhaGasto;
import com.dvml.api.entity.LinhaRequisicaoExame;
import com.dvml.api.service.LinhaRequisicaoExameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class LinhaRequisicaoExameController {
    @Autowired
    private LinhaRequisicaoExameService service;

    @GetMapping("linharequisicaoexame/all")
    public List<LinhaRequisicaoExame> getAllReceita() {
        return service.listarTodasLinhaRequisicoes();
    }
    @GetMapping("linharequisicaoexame/{id}")
    public LinhaRequisicaoExame getAllLinhaRequisicaoById(@PathVariable long id) {
        return service.getLinhaRequisicaoById(id);
    }

    @PostMapping("linharequisicaoexame/add")
    @ResponseStatus(code = HttpStatus.CREATED)
    public ResponseEntity<String> adicionar(@RequestBody @Valid LinhaRequisicaoExame LinhaRequisicaoExame){
        return service.criar(LinhaRequisicaoExame);
    }
    @PutMapping("linharequisicaoexame/edit")
    @ResponseStatus(code = HttpStatus.CREATED)
    public void updateLInhaRequisicao(@RequestBody @Valid LinhaRequisicaoExame LinhaRequisicaoExame){
        service.update(LinhaRequisicaoExame);}

    @DeleteMapping("linharequisicaoexame/{id}")
    public void deleteequisicao(@PathVariable long id) {
        if (service.getLinhaRequisicaoById(id) != null) {
            service.deleteLinhaRequisicao(id);
        } else {
            System.out.println("ERRO...");

        }
    }

    @GetMapping("linharequisicaoexame/all/requisicao/{requisicaoExameId}")
    public List<LinhaRequisicaoExame> getLinhasByrequisicaoId(@PathVariable long requisicaoExameId) {
        return service.listarLinhasPorRequisicaoId(requisicaoExameId);
    }

    @GetMapping("linharequisicaoexame/all/inscricao/{inscricaoId}")
    public List<LinhaRequisicaoExame> getLinhasByInscricaoId(@PathVariable long inscricaoId) {
        return service.listarLinhasPorIncricaoId(inscricaoId);
    }
}
