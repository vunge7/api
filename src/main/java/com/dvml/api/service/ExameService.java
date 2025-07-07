package com.dvml.api.service;

import com.dvml.api.dto.ExameDTO;
import com.dvml.api.entity.Exame;
import com.dvml.api.entity.TipoExame;
import com.dvml.api.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExameService {

    private static final Logger logger = LoggerFactory.getLogger(ExameService.class);

    @Autowired
    private ExameRepository exameRepository;

    @Autowired
    private RequisicaoExameRepository requisicaoExameRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @Autowired
    private TipoExameRepository tipoExameRepository;

    @Autowired
    private ArmazemRepository armazemRepository;

    @Autowired
    private FilialRepository filialRepository;

    public List<ExameDTO> listarTodos() {
        logger.info("Listando todos os exames");
        List<Exame> exames = exameRepository.findAll();
        return exames.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public ExameDTO getById(Long id) {
        logger.info("Buscando exame com ID: {}", id);
        Exame exame = exameRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Exame com ID {} não encontrado", id);
                    return new IllegalArgumentException("Exame não encontrado");
                });
        return convertToDTO(exame);
    }

    public List<ExameDTO> getByPacienteId(Long pacienteId) {
        logger.info("Buscando exames para paciente ID: {}", pacienteId);
        if (!pacienteRepository.existsById(pacienteId)) {
            logger.error("Paciente com ID {} não encontrado", pacienteId);
            throw new IllegalArgumentException("Paciente não encontrado");
        }
        List<Exame> exames = exameRepository.findByPacienteId(pacienteId);
        return exames.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Transactional
    public ExameDTO criar(ExameDTO dto) {
        logger.info("Criando exame para requisição ID: {}", dto.getRequisicaoExameId());
        validateExameDTO(dto);
        Exame exame = dto.toEntity();
        Exame saved = exameRepository.save(exame);
        logger.debug("Exame criado: ID {}", saved.getId());
        return convertToDTO(saved);
    }

    @Transactional
    public ExameDTO atualizar(ExameDTO dto) {
        logger.info("Atualizando exame com ID: {}", dto.getId());
        Exame exame = exameRepository.findById(dto.getId())
                .orElseThrow(() -> {
                    logger.error("Exame com ID {} não encontrado", dto.getId());
                    return new IllegalArgumentException("Exame não encontrado");
                });
        validateExameDTO(dto);
        exame.setRequisicaoExameId(dto.getRequisicaoExameId());
        exame.setPacienteId(dto.getPacienteId());
        exame.setMedicoId(dto.getMedicoId());
        exame.setTipoExameId(dto.getTipoExameId());
        exame.setArmazemId(dto.getArmazemId());
        exame.setDataRealizacao(dto.getDataRealizacao());
        exame.setResultadoQualitativo(dto.getResultadoQualitativo());
        exame.setResultadoQuantitativo(dto.getResultadoQuantitativo());
        exame.setStatusAmostra(dto.getStatusAmostra());
        exame.setObservacoes(dto.getObservacoes());
        Exame updated = exameRepository.save(exame);
        logger.debug("Exame atualizado: ID {}", updated.getId());
        return convertToDTO(updated);
    }

    @Transactional
    public void deletar(Long id) {
        logger.info("Deletando exame com ID: {}", id);
        if (!exameRepository.existsById(id)) {
            logger.error("Exame com ID {} não encontrado", id);
            throw new IllegalArgumentException("Exame não encontrado");
        }
        exameRepository.deleteById(id);
        logger.debug("Exame com ID {} deletado", id);
    }

    private void validateExameDTO(ExameDTO dto) {
        if (dto.getRequisicaoExameId() == null || !requisicaoExameRepository.existsById(dto.getRequisicaoExameId())) {
            logger.error("Requisição de exame com ID {} não encontrada", dto.getRequisicaoExameId());
            throw new IllegalArgumentException("Requisição de exame não encontrada");
        }
        if (dto.getPacienteId() == null || !pacienteRepository.existsById(dto.getPacienteId())) {
            logger.error("Paciente com ID {} não encontrado", dto.getPacienteId());
            throw new IllegalArgumentException("Paciente não encontrado");
        }
        if (dto.getMedicoId() == null || !usuarioRepository.existsById(dto.getMedicoId())) {
            logger.error("Médico com ID {} não encontrado", dto.getMedicoId());
            throw new IllegalArgumentException("Médico não encontrado");
        }
        if (dto.getTipoExameId() == null || !tipoExameRepository.existsById(dto.getTipoExameId())) {
            logger.error("Tipo de exame com ID {} não encontrado", dto.getTipoExameId());
            throw new IllegalArgumentException("Tipo de exame não encontrado");
        }
        if (dto.getArmazemId() == null || !armazemRepository.existsById(dto.getArmazemId())) {
            logger.error("Armazém com ID {} não encontrado", dto.getArmazemId());
            throw new IllegalArgumentException("Armazém não encontrado");
        }
        if (dto.getDataRealizacao() == null) {
            logger.error("Data de realização é obrigatória");
            throw new IllegalArgumentException("Data de realização é obrigatória");
        }
    }

    private ExameDTO convertToDTO(Exame exame) {
        String nomePaciente = pacienteRepository.findById(exame.getPacienteId())
                .map(paciente -> pessoaRepository.findById(paciente.getPessoaId())
                        .map(pessoa -> pessoa.getNome() + " " + pessoa.getApelido())
                        .orElse("Paciente desconhecido"))
                .orElse("Paciente não encontrado");

        String nomeMedico = usuarioRepository.findById(exame.getMedicoId())
                .map(usuario -> funcionarioRepository.findById(usuario.getFuncionarioId())
                        .map(funcionario -> pessoaRepository.findById(funcionario.getPessoaId())
                                .map(pessoa -> pessoa.getNome() + " " + pessoa.getApelido())
                                .orElse("Médico desconhecido"))
                        .orElse("Médico não encontrado"))
                .orElse("Médico não encontrado");

        String nomeTipoExame = tipoExameRepository.findById(exame.getTipoExameId())
                .map(TipoExame::getNome)
                .orElse("Tipo de exame não encontrado");

        String nomeArmazem = armazemRepository.findById(exame.getArmazemId())
                .map(armazem -> filialRepository.findById(armazem.getFilialId())
                        .map(filial -> armazem.getDesignacao() + " (" + filial.getNome() + ")")
                        .orElse(armazem.getDesignacao()))
                .orElse("Armazém não encontrado");

        return ExameDTO.fromEntity(exame, nomePaciente, nomeMedico, nomeTipoExame, nomeArmazem);
    }
}