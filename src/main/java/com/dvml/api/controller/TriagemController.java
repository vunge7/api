package com.dvml.api.controller;

import com.dvml.api.dto.TriagemDTO;
import com.dvml.api.entity.Triagem;
import com.dvml.api.service.TriagemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TriagemController {

    @Autowired
    TriagemService service;

    @GetMapping("/triagem/all")
    public List<TriagemDTO> getAllTriagem(){
       return  service.listarTodos();
    }

    @PostMapping("/triagem/add")
    @ResponseStatus(code = HttpStatus.CREATED)
    public TriagemDTO  criarTriagem(@RequestBody Triagem triagem){
       Triagem  t = service.salvar(triagem);
      return service.convertEntityToDto(t);
    }
}
