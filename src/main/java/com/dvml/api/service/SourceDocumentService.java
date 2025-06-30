package com.dvml.api.service;

import com.dvml.api.entity.Line;
import com.dvml.api.entity.SourceDocument;
import com.dvml.api.repository.SourceDocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class SourceDocumentService {
    @Autowired
    private SourceDocumentRepository repo;
    public List<SourceDocument> listarTodosSourceDocument(){
        return repo.findAll();
    }
    public SourceDocument getSourceDocumentById(long id){
        return repo.findById(id).get();
    }



    public SourceDocument saveAndGetLast(SourceDocument sourceDocument){
        return repo.save(sourceDocument);
    }
    public ResponseEntity<String> criar(SourceDocument sourceDocument) {
        if (Objects.nonNull(repo.save(sourceDocument))) {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("SourceDocument criada com sucesso!");
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Falha ao criar SourceDocument.");
    }
    public ResponseEntity<String> update(SourceDocument sourceDocument){
        SourceDocument sourceDocumentToUpdate = repo.findById( sourceDocument.getId()).get();
        sourceDocumentToUpdate.setId(sourceDocument.getId());
        sourceDocumentToUpdate.setSourceId(sourceDocument.getSourceId());
        sourceDocumentToUpdate.setHash(sourceDocument.getHash());
        sourceDocumentToUpdate.setSourceBilling((sourceDocument.getSourceBilling()));
        sourceDocumentToUpdate.setCustomerId(sourceDocument.getCustomerId());
        sourceDocumentToUpdate.setGrossTotal(sourceDocument.getGrossTotal());
        sourceDocumentToUpdate.setCashVatschemeIndicator(sourceDocument.getCashVatschemeIndicator());
        sourceDocumentToUpdate.setHashControl(sourceDocument.getHashControl());
        sourceDocumentToUpdate.setInvoiceDate(sourceDocument.getInvoiceDate());
        sourceDocumentToUpdate.setInvoiceNo(sourceDocument.getInvoiceNo());
        sourceDocumentToUpdate.setInvoiceStatus(sourceDocument.getInvoiceStatus());
        sourceDocumentToUpdate.setSourceBilling(sourceDocument.getSourceBilling());
        sourceDocumentToUpdate.setInvoiceStatusDate(sourceDocument.getInvoiceStatusDate());
        sourceDocumentToUpdate.setInvoiceType(sourceDocument.getInvoiceType());
        sourceDocumentToUpdate.setNetTotal(sourceDocument.getNetTotal());
        sourceDocumentToUpdate.setTaxPayable(sourceDocument.getTaxPayable());
        sourceDocumentToUpdate.setThirdPartiesBillingIndicator(sourceDocument.getThirdPartiesBillingIndicator());


        if(Objects.nonNull(repo.save(sourceDocumentToUpdate))) {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("SourceDocument editada com sucesso!");
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Falha ao editar a SourceDocument.");
    }
    public  ResponseEntity<String>  deleteSourceDocument(long id) {
        if (repo.existsById(id)) {
            repo.deleteById(id);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("SourceDocument deletada com sucesso!");
        } else {
            //throw new IllegalArgumentException("Linha não encontrado com o ID: " + id);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("falha ao deletar a SourceDocument.");

        }
    }

}
