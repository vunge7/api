package com.dvml.api.controller;

import com.dvml.api.dto.InscricaoFullDTO;
import com.dvml.api.entity.Inscricao;
import com.dvml.api.service.InscricaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/inscricao")
public class InscricaoController {
    @Autowired
    InscricaoService service;


    @GetMapping("/all")
    public List<InscricaoFullDTO> getAllInscricao(){
        System.out.println("Lista todos os pacientes inscritos");
        return  service.listarInscricaoNaoTriados();
    }

    @GetMapping("/{id}")
    public InscricaoFullDTO getInscricaoById(@PathVariable long id){
        return  service.getInscricaoById(id);
    }

    @GetMapping("/all/consulta")
    public List<InscricaoFullDTO> getAllInscricaoDeEncaminhamentoConsulta(){
        return  service.listarInscricaoByEncameninhamentoConsulta();
    }

    @GetMapping("/all/{estado}")
    public List<InscricaoFullDTO> getAllInscricao(@PathVariable String estado){
        return  service.listarTodosFullByEstado(estado);
    }

    @PutMapping("/edit/{id}/{estado}/{encaminhamento}")
    public void editInscricao( @PathVariable long id,  @PathVariable String estado,  @PathVariable String encaminhamento){
          service.update(id, estado, encaminhamento);
    }

    @PutMapping("/estadocondicao/edit/{id}/{estado}")
    public void editEstadoInscricao( @PathVariable long id,  @PathVariable String estado){
        service.updateEstadoCondicaoInscricao(id, estado);
    }


    @PutMapping("/edit/tm/{id}/{cor}/{minuto}")
    public ResponseEntity editTriagemManchester(@PathVariable long id, @PathVariable String cor, @PathVariable long minuto){
        return service.updateEstadoTriagemManchester(id, cor, minuto);
    }


    @PostMapping("/add")
    @ResponseStatus(code = HttpStatus.CREATED)
    public void criarInscricao(@RequestBody Inscricao inscricao){
        service.salvar(inscricao);
    }



}
