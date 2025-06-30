package com.dvml.api.controller;


import com.dvml.api.dto.LinhaTriagemDTO;
import com.dvml.api.dto.TriagemDTO;
import com.dvml.api.entity.LinhaTriagem;
import com.dvml.api.entity.Triagem;
import com.dvml.api.service.LinhaTriagemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class LinhaTriagemController {

    @Autowired
    LinhaTriagemService service;

    @GetMapping("/linha-triagem/all")
    public List<LinhaTriagemDTO> getAllLinhas(){
        return  service.listarTodos();
    }

    @PostMapping("/linha-triagem/add")
    @ResponseStatus(code = HttpStatus.CREATED)
    public LinhaTriagemDTO criarTriagem(@RequestBody LinhaTriagem linha){
        LinhaTriagem  t = service.salvar(linha);
        return service.convertEntityToDto(t);
    }

    @PostMapping("/linha-triagem/add/all")
    @ResponseStatus(code = HttpStatus.CREATED)
    public void criarTriagem(@RequestBody List<LinhaTriagem> lista){
        for (int i = 0; i < lista.size(); i++) {
              LinhaTriagem linha =   lista.get(i);
              service.salvar(linha);
        }
      //  service.salvar(linha);
    }
}
