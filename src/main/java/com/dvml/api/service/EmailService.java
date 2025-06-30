package com.dvml.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender mailSender;

    public void sendAppointmentEmails(
            String patientEmail, String doctorEmail,
            String patientName, String doctorName,
            String appointmentDate, String appointmentTime,
            String consultationType) throws MessagingException {

        // E-mail para o paciente
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

        // E-mail para o médico
        MimeMessage doctorMessage = mailSender.createMimeMessage();
        MimeMessageHelper doctorHelper = new MimeMessageHelper(doctorMessage, true, "UTF-8");
        doctorHelper.setFrom("robbiealgon@gmail.com");
        doctorHelper.setTo(doctorEmail);
        doctorHelper.setSubject("Nova Consulta Agendada");
        doctorHelper.setText(
                "<h3>Olá, Dr(a). " + doctorName + "</h3>" +
                        "<p>Uma nova consulta foi agendada com você.</p>" +
                        "<p><strong>Detalhes da Consulta:</strong></p>" +
                        "<ul>" +
                        "<li><strong>Paciente:</strong> " + patientName + "</li>" +
                        "<li><strong>Data:</strong> " + appointmentDate + "</li>" +
                        "<li><strong>Hora:</strong> " + appointmentTime + "</li>" +
                        "<li><strong>Tipo de Consulta:</strong> " + (consultationType != null ? consultationType : "Consulta") + "</li>" +
                        "</ul>" +
                        "<p>Por favor, prepare-se para a consulta.</p>",
                true
        );
        mailSender.send(doctorMessage);
    }
}