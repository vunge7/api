package com.dvml.api.service;

import com.dvml.api.util.teste.QrData;
import org.springframework.stereotype.Service;

@Service
public class QrDataService {

    private QrData ultimoQrData;

    public void salvar(QrData data) {
        this.ultimoQrData = data;
    }

    public QrData obter() {
        return ultimoQrData;
    }

    public void limpar() {
        this.ultimoQrData = null;
    }
}
