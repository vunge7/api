package com.dvml.api.controller;

import com.dvml.api.dto.SubsidioDTO;
import com.dvml.api.service.SubsidioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class SubsidioController {

    @Autowired
    private SubsidioService subsidioService;

    @PostMapping("subsidio/add")
    public ResponseEntity<SubsidioDTO> create(@Valid @RequestBody SubsidioDTO subsidioDTO) {
        SubsidioDTO created = subsidioService.create(subsidioDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("subsidio/{id}")
    public ResponseEntity<SubsidioDTO> update(@PathVariable("id") Long id, @Valid @RequestBody SubsidioDTO subsidioDTO) {
        SubsidioDTO updated = subsidioService.update(id, subsidioDTO);
        return ResponseEntity.ok(updated);
    }

    @GetMapping("subsidio/{id}")
    public ResponseEntity<SubsidioDTO> findById(@PathVariable("id") Long id) {
        SubsidioDTO subsidioDTO = subsidioService.findById(id);
        return ResponseEntity.ok(subsidioDTO);
    }

    @GetMapping("subsidio/all")
    public ResponseEntity<List<SubsidioDTO>> findAll() {
        List<SubsidioDTO> subsidios = subsidioService.findAll();
        return ResponseEntity.ok(subsidios);
    }

    @DeleteMapping("subsidio/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        subsidioService.delete(id);
        return ResponseEntity.noContent().build();
    }
}