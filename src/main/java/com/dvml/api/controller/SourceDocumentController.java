package com.dvml.api.controller;


import com.dvml.api.entity.SourceDocument;
import com.dvml.api.service.SourceDocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/sourceDocument")
public class SourceDocumentController {
    @Autowired
    private SourceDocumentService service;
    @GetMapping("/all")
    public List<SourceDocument> getAllSourceDocument(){
        return service.listarTodosSourceDocument();
    }

    @GetMapping("/{id}")
    public SourceDocument getAllSourceDocumentById(@PathVariable long id){
        return service.getSourceDocumentById(id);
    }

    @PostMapping("/add")
    @ResponseStatus(code = HttpStatus.CREATED)
    public ResponseEntity<String> adicionar(@RequestBody SourceDocument sourcedocument){
        System.out.println("InvoiceNo: " +sourcedocument.getInvoiceNo());
        System.out.println("InvoiceSatus: " +sourcedocument.getInvoiceStatus());
        System.out.println("SourceId: " +sourcedocument.getSourceId());
        return service.criar(sourcedocument);
    }

    @PostMapping("/add/last")
    @ResponseStatus(code = HttpStatus.CREATED)
    public SourceDocument adicionarUltimoDocumento(@RequestBody SourceDocument sourcedocument){
        return service.saveAndGetLast(sourcedocument);
    }

    @PutMapping("/edit")
    @ResponseStatus(code = HttpStatus.CREATED)
    public void updateSourceDocument(@RequestBody @Valid SourceDocument sourcedocument){
        service.update(sourcedocument);}

    @DeleteMapping("/{id}")
    public void deleteSourceDocument(@PathVariable long id) {
        if (service.getSourceDocumentById(id) != null) {
            service.deleteSourceDocument(id);
        } else {
            System.out.println("ERRO...");
        }

    }
}
