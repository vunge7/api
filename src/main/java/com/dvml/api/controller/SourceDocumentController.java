package com.dvml.api.controller;


import com.dvml.api.entity.SourceDocument;
import com.dvml.api.service.SourceDocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class SourceDocumentController {
    @Autowired
    private SourceDocumentService service;
    @GetMapping("sourceDocument/all")
    public List<SourceDocument> getAllSourceDocument(){
        return service.listarTodosSourceDocument();
    }

    @GetMapping("sourceDocument/{id}")
    public SourceDocument getAllSourceDocumentById(@PathVariable long id){
        return service.getSourceDocumentById(id);
    }

    @PostMapping("sourceDocument/add")
    @ResponseStatus(code = HttpStatus.CREATED)
    public ResponseEntity<String> adicionar(@RequestBody SourceDocument sourcedocument){
        System.out.println("InvoiceNo: " +sourcedocument.getInvoiceNo());
        System.out.println("InvoiceSatus: " +sourcedocument.getInvoiceStatus());
        System.out.println("SourceId: " +sourcedocument.getSourceId());
        return service.criar(sourcedocument);
    }

    @PostMapping("sourceDocument/add/last")
    @ResponseStatus(code = HttpStatus.CREATED)
    public SourceDocument adicionarUltimoDocumento(@RequestBody SourceDocument sourcedocument){
        return service.saveAndGetLast(sourcedocument);
    }

    @PutMapping("/sourceDocument/edit")
    @ResponseStatus(code = HttpStatus.CREATED)
    public void updateSourceDocument(@RequestBody @Valid SourceDocument sourcedocument){
        service.update(sourcedocument);}

    @DeleteMapping("/sourceDocument/{id}")
    public void deleteSourceDocument(@PathVariable long id) {
        if (service.getSourceDocumentById(id) != null) {
            service.deleteSourceDocument(id);
        } else {
            System.out.println("ERRO...");
        }

    }
}
