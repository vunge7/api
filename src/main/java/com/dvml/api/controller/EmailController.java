package com.dvml.api.controller;

import com.dvml.api.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class EmailController {

    @Autowired
    private EmailService emailService;

    @PostMapping("enviar-email")
    public ResponseEntity<?> sendAppointmentEmails(@RequestBody Map<String, String> request) {
        try {
            String patientEmail = request.get("pacienteEmail");
            String doctorEmail = request.get("dotorEmail");
            String patientName = request.get("pacienteNome");
            String doctorName = request.get("dotorNome");
            String appointmentDate = request.get("data");
            String appointmentTime = request.get("hora");
            String consultationType = request.get("consulta");

            if (patientEmail == null || doctorEmail == null || patientName == null || doctorName == null ||
                    appointmentDate == null || appointmentTime == null) {
                return ResponseEntity.badRequest().body("Campos obrigatórios faltando");
            }

            emailService.sendAppointmentEmails(
                    patientEmail, doctorEmail,
                    patientName, doctorName,
                    appointmentDate, appointmentTime,
                    consultationType
            );

            return ResponseEntity.ok("E-mails enviados com sucesso");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Erro ao enviar e-mails: " + e.getMessage());
        }
    }
}
