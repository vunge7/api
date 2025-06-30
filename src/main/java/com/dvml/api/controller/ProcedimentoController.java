package com.dvml.api.controller;

import com.dvml.api.entity.Pessoa;
import com.dvml.api.entity.Procedimento;
import com.dvml.api.service.PessoaService;
import com.dvml.api.service.ProcedimentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class ProcedimentoController {

    @Autowired
    private ProcedimentoService service;

    @GetMapping("procedimento/all")
    public List<Procedimento> getAllPessoas() {
        return service.listarTodosProcedimentos();
    }

  /*  @GetMapping("procedimento/{id}")
    public Procedimento getAllProcedimentoById(@PathVariable long id) {
        return service.getProcedimentoById(id);
    }
   */

    /*@PostMapping("procedimento/add")
    @ResponseStatus(code = HttpStatus.CREATED)
    public ResponseEntity<String> adicionar(@RequestBody @Valid Procedimento procedimento){
        return service.criar(procedimento);
    }*/

    @PutMapping("procedimento/edit")
    @ResponseStatus(code = HttpStatus.CREATED)
    public void updateProcedimento(@RequestBody @Valid Procedimento procedimento){
        service.update(procedimento);}

   /* @DeleteMapping("procedimento/{id}")
    public void deleteProcedimento(@PathVariable long id) {
       if (service.getProcedimentoById(id) != null) {
            service.deleteProcedimento(id);
        } else {
            System.out.println("ERRO...");
        }

    }*/
}
