package com.dvml.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.core.io.ClassPathResource;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender mailSender;

    public void sendAppointmentEmails(
            String patientEmail, String doctorEmail,
            String patientName, String doctorName,
            String appointmentDate, String appointmentTime,
            String consultationType) throws Exception {

        // ... (envio do e-mail do paciente igual ao seu código)

        // Geração do PDF com Jasper
        Map<String, Object> params = new HashMap<>();
        params.put("doctorName", doctorName);
        params.put("patientName", patientName);
        params.put("appointmentDate", appointmentDate);
        params.put("appointmentTime", appointmentTime);
        params.put("consultationType", consultationType);

        // Carrega o template .jrxml
        InputStream reportStream = new ClassPathResource("reports/jasper/agendamento.jrxml").getInputStream();
        JasperReport jasperReport = JasperCompileManager.compileReport(reportStream);

        // Gera o PDF em memória
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, new JREmptyDataSource());
        byte[] pdfBytes = JasperExportManager.exportReportToPdf(jasperPrint);

        // E-mail para o médico com anexo PDF
        MimeMessage doctorMessage = mailSender.createMimeMessage();
        MimeMessageHelper doctorHelper = new MimeMessageHelper(doctorMessage, true, "UTF-8");
        doctorHelper.setFrom("robbiealgon@gmail.com");
        doctorHelper.setTo(doctorEmail);
        doctorHelper.setSubject("Nova Consulta Agendada");
        doctorHelper.setText(
                "<h3>Olá, Dr(a). " + doctorName + "</h3>" +
                        "<p>Uma nova consulta foi agendada com você.</p>" +
                        "<p>Veja os detalhes no PDF em anexo.</p>",
                true
        );
        doctorHelper.addAttachment("agendamento.pdf", new ByteArrayResource(pdfBytes));
        mailSender.send(doctorMessage);
    }
}