package com.dvml.api.service;

import com.dvml.api.dto.AgendaDTO;
import com.dvml.api.dto.LinhaAgendaDTO;
import com.dvml.api.dto.MedicoDTO;
import com.dvml.api.entity.*;
import com.dvml.api.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MedicoService {

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PessoaRepository PessoaRepository;

    @Autowired
    private AgendaRepository AgendaRepository;

    @Autowired
    private LinhaAgendaRepository LinhaAgendaRepository;

    // Busca um médico por ID
    public ResponseEntity<?> getMedicoById(Long id) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(id);

        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();

            Optional<Funcionario> funcionarioOpt = funcionarioRepository.findById(usuario.getFuncionarioId());
            if (funcionarioOpt.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Funcionário não encontrado para o usuário com ID: " + id);
            }
            Funcionario funcionario = funcionarioOpt.get();

            Optional<Pessoa> pessoaOpt = PessoaRepository.findById(funcionario.getPessoaId());
            if (pessoaOpt.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Dados pessoais não encontrados para o funcionário com ID: " + funcionario.getId());
            }
            Pessoa pessoa = pessoaOpt.get();

            MedicoDTO medicoDTO = new MedicoDTO(
                    usuario.getId(), // Usando o ID do usuário como ID do médico
                    pessoa.getNome(),
                    usuario.getNumeroOrdem(),
                    usuario.getUserName(),
                    pessoa.getTelefone(),
                    pessoa.getEmail(),
                    pessoa.getDataNascimento(),
                    funcionario.getDataAdmissao(),
                    funcionario.getTipoContrato().name(), // Corrigido de getTipoDeContrato() para getTipoContrato().name()
                    funcionario.getSalario(),
                    new BigDecimal(0d),
                    funcionario.getDescricao(),
                    funcionario.getFechoPeriodo().name(), // Corrigido de getFechoContas() para getFechoPeriodo().name()
                    pessoa.getEndereco(),
                    pessoa.getGenero(),
                    usuario.getId(),
                    funcionario.getId()
            );

            return ResponseEntity.ok(medicoDTO);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Médico não encontrado com ID: " + id);
        }
    }

    // Lista todas as agendas de um médico por funcionarioId
    public List<AgendaDTO> getAgendasByMedico(Long funcionarioId) {
        List<LinhaAgenda> linhas = LinhaAgendaRepository.findByFuncionarioId(funcionarioId);

        List<Long> agendaIds = linhas.stream()
                .map(LinhaAgenda::getAgendaId)
                .distinct()
                .collect(Collectors.toList());

        List<Agenda> agendas = AgendaRepository.findAllById(agendaIds);

        return agendas.stream()
                .map(agenda -> {
                    List<LinhaAgendaDTO> linhaDTOs = LinhaAgendaRepository.findByAgendaId(agenda.getId())
                            .stream()
                            .map(linha -> new LinhaAgendaDTO(
                                    linha.getId(),
                                    linha.getAgendaId(),
                                    linha.getConsultaId(),
                                    linha.getFuncionarioId(),
                                    linha.getPacienteId(),
                                    linha.getDataRealizacao(),
                                    linha.getStatus()
                            ))
                            .collect(Collectors.toList());

                    return new AgendaDTO(
                            agenda.getId(),
                            agenda.getDescricao(),
                            linhaDTOs
                    );
                })
                .collect(Collectors.toList());
    }

    // Método adicional: Lista todos os médicos
    public List<MedicoDTO> getAllMedicos() {
        List<Usuario> usuarios = usuarioRepository.findAll(); // Assumindo que todos os usuários são médicos

        return usuarios.stream()
                .map(usuario -> {
                    Optional<Funcionario> funcionarioOpt = funcionarioRepository.findById(usuario.getFuncionarioId());
                    Optional<Pessoa> pessoaOpt = funcionarioOpt.isPresent() ?
                            PessoaRepository.findById(funcionarioOpt.get().getPessoaId()) :
                            Optional.empty();

                    if (funcionarioOpt.isPresent() && pessoaOpt.isPresent()) {
                        Funcionario funcionario = funcionarioOpt.get();
                        Pessoa pessoa = pessoaOpt.get();

                        return new MedicoDTO(
                                usuario.getId(),
                                pessoa.getNome(),
                                usuario.getNumeroOrdem(),
                                usuario.getUserName(),
                                pessoa.getTelefone(),
                                pessoa.getEmail(),
                                pessoa.getDataNascimento(),
                                funcionario.getDataAdmissao(),
                                funcionario.getTipoContrato().name(), // Corrigido de getTipoDeContrato() para getTipoContrato().name()
                                funcionario.getSalario(),
                                new BigDecimal(0d),
                                funcionario.getDescricao(),
                                funcionario.getFechoPeriodo().name(), // Corrigido de getFechoContas() para getFechoPeriodo().name()
                                pessoa.getEndereco(),
                                pessoa.getGenero(),
                                usuario.getId(),
                                funcionario.getId()
                        );
                    }
                    return null; // Ou lançar uma exceção, dependendo do requisito
                })
                .filter(medicoDTO -> medicoDTO != null) // Filtra médicos inválidos
                .collect(Collectors.toList());
    }
}