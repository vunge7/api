package com.dvml.api.controller;

import com.dvml.api.dto.AgendaAppDTO;
import com.dvml.api.service.AgendaAppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class AgendaAppController {

    @Autowired
    private AgendaAppService service;

    @PostMapping("agenda/app/add")
    public ResponseEntity<?> criarAgendaViaApp(@RequestBody AgendaAppDTO dto) {
        return service.criarAgendaViaApp(dto);
    }
}
