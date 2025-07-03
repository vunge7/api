package com.dvml.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.internet.MimeMessage;

import java.io.File;
import java.util.HashMap;

import com.dvml.api.util.TransformReportToPDF;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender mailSender;

    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);

    public void sendAppointmentEmails(
            String patientEmail, String doctorEmail,
            String patientName, String doctorName,
            String appointmentDate, String appointmentTime,
            String consultationType,
            Long medicoId // ID do médico
    ) throws Exception {

        // Checa e-mails
        if (patientEmail == null || patientEmail.isEmpty() || doctorEmail == null || doctorEmail.isEmpty()) {
            logger.warn("E-mail do paciente ou médico está vazio. Não será enviado.");
            return;
        }

        // 1. Envia e-mail para o paciente (sem anexo)
        MimeMessage patientMessage = mailSender.createMimeMessage();
        MimeMessageHelper patientHelper = new MimeMessageHelper(patientMessage, true, "UTF-8");
        patientHelper.setFrom("robbiealgon@gmail.com");
        patientHelper.setTo(patientEmail);
        patientHelper.setSubject("Confirmação de Agendamento de Consulta");
        patientHelper.setText(
                "<h3>Olá, " + patientName + "</h3>" +
                        "<p>Sua consulta foi agendada com sucesso!</p>" +
                        "<p><strong>Detalhes da Consulta:</strong></p>" +
                        "<ul>" +
                        "<li><strong>Médico:</strong> " + doctorName + "</li>" +
                        "<li><strong>Data:</strong> " + appointmentDate + "</li>" +
                        "<li><strong>Hora:</strong> " + appointmentTime + "</li>" +
                        "<li><strong>Tipo de Consulta:</strong> " + (consultationType != null ? consultationType : "Consulta") + "</li>" +
                        "</ul>" +
                        "<p>Agradecemos pela confiança!</p>",
                true
        );
        mailSender.send(patientMessage);
        logger.info("E-mail de confirmação enviado para paciente: {}", patientEmail);

        // 2. Gera o PDF do agendamento para o médico
        String jasperTemplate = "agendamento"; // nome fixo do template
        String pdfFileName = "agendamento_" + medicoId + "_" + System.currentTimeMillis(); // nome único para o PDF
        HashMap<String, Object> hash = new HashMap<>();
        hash.put("id", medicoId); // ou o nome do parâmetro que seu relatório espera
        new TransformReportToPDF(jasperTemplate, hash, pdfFileName);

        // Caminho do PDF gerado
        String filePath = "reports/pdf/" + pdfFileName + ".pdf";
        File pdfFile = new File(filePath);

        if (!pdfFile.exists()) {
            logger.error("PDF não encontrado para envio ao médico: {}", filePath);
            return;
        }

        // 3. Envia e-mail para o médico com o PDF em anexo
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
        // Anexa o PDF
        doctorHelper.addAttachment("agendamento.pdf", new FileSystemResource(pdfFile));
        mailSender.send(doctorMessage);
        logger.info("E-mail de agendamento enviado para médico: {}", doctorEmail);

        // (Opcional) Apagar o PDF após envio, se não quiser acumular arquivos
        // pdfFile.delete();
    }

    // Método para envio da lista de consultas do dia para o médico
    public void sendListaConsultasParaMedico(String medicoEmail, String medicoNome, String corpoEmail) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setFrom("robbiealgon@gmail.com");
            helper.setTo(medicoEmail);
            helper.setSubject("Lista de Consultas do Dia");
            helper.setText(
                    "<h3>Olá, Dr(a). " + medicoNome + "</h3>" +
                            "<p>Segue a lista de consultas marcadas para o dia:</p>" +
                            corpoEmail,
                    true
            );
            mailSender.send(message);
            logger.info("Lista de consultas enviada para médico: {}", medicoEmail);
        } catch (Exception e) {
            logger.error("Erro ao enviar lista de consultas para médico: {}", medicoEmail, e);
        }
    }
}