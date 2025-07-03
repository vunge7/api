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
    public ResponseEntity<?> sendAppointmentEmails(@RequestBody Map<String, Object> request) {
        try {
            String patientEmail = (String) request.get("pacienteEmail");
            String doctorEmail = (String) request.get("dotorEmail");
            String patientName = (String) request.get("pacienteNome");
            String doctorName = (String) request.get("dotorNome");
            String appointmentDate = (String) request.get("data");
            String appointmentTime = (String) request.get("hora");
            String consultationType = (String) request.get("consulta");
            // Recebe o ID do médico (funcionarioId)
            Long medicoId = null;
            if (request.get("funcionarioId") instanceof Integer) {
                medicoId = ((Integer) request.get("funcionarioId")).longValue();
            } else if (request.get("funcionarioId") instanceof Long) {
                medicoId = (Long) request.get("funcionarioId");
            } else if (request.get("funcionarioId") instanceof String) {
                medicoId = Long.parseLong((String) request.get("funcionarioId"));
            }

            if (patientEmail == null || doctorEmail == null || patientName == null || doctorName == null ||
                    appointmentDate == null || appointmentTime == null || medicoId == null) {
                return ResponseEntity.badRequest().body("Campos obrigatórios faltando");
            }

            emailService.sendAppointmentEmails(
                    patientEmail, doctorEmail,
                    patientName, doctorName,
                    appointmentDate, appointmentTime,
                    consultationType,
                    medicoId
            );

            return ResponseEntity.ok("E-mails enviados com sucesso");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Erro ao enviar e-mails: " + e.getMessage());
        }
    }
}