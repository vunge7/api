package com.dvml.api.service;

import com.dvml.api.entity.*;
import com.dvml.api.repository.*;
import com.dvml.api.service.EmailService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class AgendaSchedulerService {

    private static final Logger logger = LoggerFactory.getLogger(AgendaSchedulerService.class);

    @Autowired
    private LinhaAgendaRepository linhaAgendaRepository;
    @Autowired
    private PacienteRepository pacienteRepository;
    @Autowired
    private FuncionarioRepository funcionarioRepository;
    @Autowired
    private PessoaRepository pessoaRepository;
    @Autowired
    private AgendaRepository agendaRepository; // Substitui ConsultaRepository
    @Autowired
    private EmailService emailService;

    // Executa todo dia à meia-noite
    @Scheduled(cron = "0 0 0 * * *")
    public void enviarLembretesTresDiasAntes() {
        LocalDate dataAlvo = LocalDate.now().plusDays(3);

        Date startOfDay = Date.from(dataAlvo.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date endOfDay = Date.from(dataAlvo.plusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant());

        List<LinhaAgenda> linhasAgenda = linhaAgendaRepository.findByDataRealizacaoBetween(startOfDay, endOfDay);

        for (LinhaAgenda linha : linhasAgenda) {
            try {
                Optional<Paciente> pacienteOpt = pacienteRepository.findById(linha.getPacienteId());
                Optional<Funcionario> medicoOpt = funcionarioRepository.findById(linha.getFuncionarioId());
                Optional<Agenda> agendaOpt = agendaRepository.findById(linha.getAgendaId());

                Pessoa pessoaPaciente = pacienteOpt
                        .flatMap(p -> pessoaRepository.findById(p.getPessoaId()))
                        .orElse(null);

                Pessoa pessoaMedico = medicoOpt
                        .flatMap(m -> pessoaRepository.findById(m.getPessoaId()))
                        .orElse(null);

                Agenda agenda = agendaOpt.orElse(null);

                // Checa se os e-mails existem
                if (pessoaPaciente != null && pessoaMedico != null
                        && pessoaPaciente.getEmail() != null && pessoaMedico.getEmail() != null) {

                    // Formata data e hora
                    Date dataRealizacao = linha.getDataRealizacao();
                    LocalDateTime localDateTime = dataRealizacao.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
                    String dataFormatada = localDateTime.toLocalDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                    String horaFormatada = localDateTime.toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm"));

                    emailService.sendAppointmentEmails(
                            pessoaPaciente.getEmail(),
                            pessoaMedico.getEmail(),
                            pessoaPaciente.getNome(),
                            pessoaMedico.getNome(),
                            dataFormatada,
                            horaFormatada,
                            agenda != null ? agenda.getDescricao() : null,
                            medicoOpt.get().getId()
                    );
                } else {
                    logger.warn("Dados de e-mail ausentes para paciente ou médico na linhaAgenda id={}", linha.getId());
                }
            } catch (Exception e) {
                logger.error("Erro ao enviar e-mail para linhaAgenda id=" + linha.getId(), e);
            }
        }
    }
}