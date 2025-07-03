package com.dvml.api.controller;
// QrDataController.java
import com.dvml.api.service.QrDataService;
import com.dvml.api.util.teste.QrData;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/qr-data")
public class QrDataController {

    private final QrDataService service;

    public QrDataController(QrDataService service) {
        this.service = service;
    }

    @PostMapping
    public void salvar(@RequestBody QrData data) {
        System.out.println(data.getId());
        service.salvar(data);
    }

    @GetMapping
    public QrData obter() {
        return service.obter();
    }

    @DeleteMapping
    public void limpar() {
        service.limpar();
    }
}
