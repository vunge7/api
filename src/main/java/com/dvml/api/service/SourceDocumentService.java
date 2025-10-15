package com.dvml.api.service;

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

    // Listar todos os SourceDocument
    public List<SourceDocument> listarTodosSourceDocument() {
        return repo.findAll();
    }

    // Buscar SourceDocument por ID
    public SourceDocument getSourceDocumentById(long id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("SourceDocument não encontrada com ID: " + id));
    }

    // Salvar e retornar o último inserido
    public SourceDocument saveAndGetLast(SourceDocument sourceDocument) {
        return repo.save(sourceDocument); // empresaId será salvo se preenchido
    }

    // Criar SourceDocument
    public ResponseEntity<String> criar(SourceDocument sourceDocument) {
        if (Objects.nonNull(repo.save(sourceDocument))) {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("SourceDocument criada com sucesso!");
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Falha ao criar SourceDocument.");
    }

    // Atualizar SourceDocument existente
    public ResponseEntity<String> update(SourceDocument sourceDocument) {
        SourceDocument sourceDocumentToUpdate = repo.findById(sourceDocument.getId())
                .orElseThrow(() -> new RuntimeException("SourceDocument não encontrada com ID: " + sourceDocument.getId()));

        sourceDocumentToUpdate.setInvoiceNo(sourceDocument.getInvoiceNo());
        sourceDocumentToUpdate.setInvoiceStatus(sourceDocument.getInvoiceStatus());
        sourceDocumentToUpdate.setInvoiceStatusDate(sourceDocument.getInvoiceStatusDate());
        sourceDocumentToUpdate.setSourceId(sourceDocument.getSourceId());
        sourceDocumentToUpdate.setSourceBilling(sourceDocument.getSourceBilling());
        sourceDocumentToUpdate.setHash(sourceDocument.getHash());
        sourceDocumentToUpdate.setHashControl(sourceDocument.getHashControl());
        sourceDocumentToUpdate.setInvoiceDate(sourceDocument.getInvoiceDate());
        sourceDocumentToUpdate.setInvoiceType(sourceDocument.getInvoiceType());
        sourceDocumentToUpdate.setSelfBillingIndicator(sourceDocument.getSelfBillingIndicator());
        sourceDocumentToUpdate.setCashVatschemeIndicator(sourceDocument.getCashVatschemeIndicator());
        sourceDocumentToUpdate.setThirdPartiesBillingIndicator(sourceDocument.getThirdPartiesBillingIndicator());
        sourceDocumentToUpdate.setSystemEntryDate(sourceDocument.getSystemEntryDate());
        sourceDocumentToUpdate.setTaxPayable(sourceDocument.getTaxPayable());
        sourceDocumentToUpdate.setNetTotal(sourceDocument.getNetTotal());
        sourceDocumentToUpdate.setGrossTotal(sourceDocument.getGrossTotal());
        sourceDocumentToUpdate.setDiscountTotal(sourceDocument.getDiscountTotal());
        sourceDocumentToUpdate.setCustomerId(sourceDocument.getCustomerId());
        sourceDocumentToUpdate.setEmpresaId(sourceDocument.getEmpresaId()); // <--- empresaId adicionado

        if (Objects.nonNull(repo.save(sourceDocumentToUpdate))) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body("SourceDocument editada com sucesso!");
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Falha ao editar a SourceDocument.");
    }

    // Deletar SourceDocument
    public ResponseEntity<String> deleteSourceDocument(long id) {
        if (repo.existsById(id)) {
            repo.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK)
                    .body("SourceDocument deletada com sucesso!");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Falha ao deletar a SourceDocument.");
        }
    }
}
