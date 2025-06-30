package com.dvml.api.service;

import com.dvml.api.entity.Line;
import com.dvml.api.repository.LineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.Objects;

import java.util.List;

@Service

public class LineService {
    @Autowired
    private LineRepository repo;

    @Autowired
    private LineRepository liRepository;

    public List<Line> listarTodasLinhas() {
        return liRepository.findAllOrderByNomeAsc();
    }

    public Line getLineById(long id) {
        return repo.findById(id).get();
    }

       public ResponseEntity<String> criar(Line line) {
        if (Objects.nonNull(repo.save(line))) {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("Linha criada com sucesso!");
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Falha ao criar o Linha.");
    }
    public ResponseEntity<String> update(Line line){
        Line LineToUpdate = repo.findById(line.getId()).get();
        LineToUpdate.setProductCode(line.getProductCode());
        LineToUpdate.setLineNumber(line.getLineNumber());
        LineToUpdate.setTaxCode(line.getTaxCode());
        LineToUpdate.setProductDescription((line.getProductDescription()));
        LineToUpdate.setCreditAmount(line.getCreditAmount());
        LineToUpdate.setDebitAmount(line.getDebitAmount());
        LineToUpdate.setQuantity(line.getQuantity());
        LineToUpdate.setReference(line.getReference());
        LineToUpdate.setTaxBase(line.getTaxBase());
        LineToUpdate.setTaxCountryRegion(line.getTaxCountryRegion());
        LineToUpdate.setTaxExceptionCode(line.getTaxExceptionCode());
        LineToUpdate.setTaxExceptionReason(line.getTaxExceptionReason());
        LineToUpdate.setTaxPercentage(line.getTaxPercentage());
        LineToUpdate.setTaxPointDate(line.getTaxPointDate());
        LineToUpdate.setTaxType(line.getTaxType());
        LineToUpdate.setUnitOfMeasure(line.getUnitOfMeasure());
        LineToUpdate.setUnitPrice(line.getUnitPrice());
        LineToUpdate.setSourceDocumentId(line.getSourceDocumentId());

        if(Objects.nonNull(repo.save(LineToUpdate))) {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("Linha editada com sucesso!");
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Falha ao editar a Linha.");
    }
    public  ResponseEntity<String> deleteLine(long id) {
        if (repo.existsById(id)) {
            repo.deleteById(id);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("Linha deletada com sucesso!");
        } else {
            //throw new IllegalArgumentException("Linha não encontrado com o ID: " + id);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Falha ao deletar a Linha.");
        }

    }

}