package com.dvml.api.service;

import com.dvml.api.dto.RequisicaoExameDTO;
import com.dvml.api.entity.*;
import com.dvml.api.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects; // Import adicionado para corrigir "Cannot resolve symbol 'Objects'"
import java.util.Optional;

@Service
public class RequisicaoExameService {
    private static final Logger logger = LoggerFactory.getLogger(RequisicaoExameService.class);

    @Autowired
    private RequisicaoExameRepository repo;

    @Autowired
    private UsuarioRepository userRepo;

    @Autowired
    private PessoaRepository pessoaRepo;

    @Autowired
    private FuncionarioRepository funcionarioRepo;

    @Autowired
    private InscricaoRepository inscricaoRepo;

    @Autowired
    private PacienteRepository pacienteRepo;

    public RequisicaoExame getRequisicaoById(long id) {
        logger.info("Buscando requisição com ID: {}", id);
        Optional<RequisicaoExame> requisicao = repo.findById(id);
        if (requisicao.isEmpty()) {
            logger.warn("Requisição com ID {} não encontrada", id);
            throw new RuntimeException("Requisição não encontrada");
        }
        return requisicao.get();
    }

    public List<RequisicaoExame> listarTodasRequisicoes() {
        logger.info("Listando todas as requisições de exame");
        List<RequisicaoExame> requisicoes = repo.findAllOrderByNomeAsc();
        logger.info("Encontradas {} requisições", requisicoes.size());
        return requisicoes;
    }

    public List<RequisicaoExameDTO> listarTodasRequisicoesComposto() {
        logger.info("Listando todas as requisições compostas");
        try {
            List<RequisicaoExame> listaRequisicao = repo.findAllOrderByNomeAsc();
            List<RequisicaoExameDTO> listaRequisicaoDTO = new ArrayList<>();

            for (RequisicaoExame l : listaRequisicao) {
                try {
                    RequisicaoExameDTO linha = new RequisicaoExameDTO();
                    linha.setId(l.getId());
                    Pessoa medicoPessoa = getPessoaByUsuario(l.getUsuarioId());
                    linha.setMedico(medicoPessoa.getNome() + " " + medicoPessoa.getApelido());
                    Pessoa pacientePessoa = getPessoaByInscricao(l.getInscricaoId());
                    linha.setPaciente(pacientePessoa.getNome() + " " + pacientePessoa.getApelido());
                    linha.setData(l.getDataRequisicao());
                    linha.setEmpresaId(l.getEmpresaId());
                    listaRequisicaoDTO.add(linha);
                } catch (Exception e) {
                    logger.warn("Erro ao processar requisição ID {}: {}", l.getId(), e.getMessage());
                    // Continuar com o próximo item
                }
            }
            logger.info("Encontradas {} requisições compostas", listaRequisicaoDTO.size());
            return listaRequisicaoDTO;
        } catch (Exception e) {
            logger.error("Erro ao listar requisições compostas: {}", e.getMessage(), e);
            return new ArrayList<>();
        }
    }

    private Pessoa getPessoaByInscricao(long idInscricao) {
        logger.debug("Buscando pessoa por inscrição ID: {}", idInscricao);
        try {
            Inscricao inscricao = inscricaoRepo.findById(idInscricao)
                    .orElseThrow(() -> new RuntimeException("Inscrição não encontrada"));
            Paciente paciente = pacienteRepo.findById(inscricao.getPacienteId())
                    .orElseThrow(() -> new RuntimeException("Paciente não encontrado"));
            return pessoaRepo.findById(paciente.getPessoaId())
                    .orElseThrow(() -> new RuntimeException("Pessoa não encontrada"));
        } catch (Exception e) {
            logger.error("Erro ao buscar pessoa por inscrição ID {}: {}", idInscricao, e.getMessage());
            throw e;
        }
    }

    private Pessoa getPessoaByUsuario(long idUser) {
        logger.debug("Buscando pessoa por usuário ID: {}", idUser);
        try {
            Usuario usuario = userRepo.findById(idUser)
                    .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
            Funcionario funcionario = funcionarioRepo.findById(usuario.getFuncionarioId())
                    .orElseThrow(() -> new RuntimeException("Funcionário não encontrado"));
            return pessoaRepo.findById(funcionario.getPessoaId())
                    .orElseThrow(() -> new RuntimeException("Pessoa não encontrada"));
        } catch (Exception e) {
            logger.error("Erro ao buscar pessoa por usuário ID {}: {}", idUser, e.getMessage());
            throw e;
        }
    }

    public RequisicaoExame criar(RequisicaoExame requisicaoExame) {
        logger.info("Criando nova requisição: {}", requisicaoExame);
        try {
            if (requisicaoExame.getDataRequisicao() == null) {
                logger.info("Campo 'dataRequisicao' não fornecido, definindo como data atual");
                requisicaoExame.setDataRequisicao(new Date());
            }
            RequisicaoExame saved = repo.save(requisicaoExame);
            logger.info("Requisição criada com ID: {}", saved.getId());
            return saved;
        } catch (Exception e) {
            logger.error("Erro ao criar requisição: {}", e.getMessage(), e);
            throw new RuntimeException("Erro ao criar requisição: " + e.getMessage());
        }
    }

    public ResponseEntity<String> update(RequisicaoExame requisicaoExame) {
        logger.info("Atualizando requisição com ID: {}", requisicaoExame.getId());
        try {
            // Removida verificação de id == null, pois id é long (primitivo)
            Optional<RequisicaoExame> opt = repo.findById(requisicaoExame.getId());
            if (opt.isEmpty()) {
                logger.warn("Requisição com ID {} não encontrada", requisicaoExame.getId());
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Requisição não encontrada!");
            }

            RequisicaoExame requisicaoToUpdate = opt.get();
            requisicaoToUpdate.setDataRequisicao(requisicaoExame.getDataRequisicao());
            requisicaoToUpdate.setStatus(requisicaoExame.getStatus());
            requisicaoToUpdate.setUsuarioId(requisicaoExame.getUsuarioId());
            requisicaoToUpdate.setInscricaoId(requisicaoExame.getInscricaoId());
            requisicaoToUpdate.setEmpresaId(requisicaoExame.getEmpresaId());

            RequisicaoExame saved = repo.save(requisicaoToUpdate);
            if (Objects.nonNull(saved)) {
                logger.info("Requisição com ID {} atualizada com sucesso", saved.getId());
                return ResponseEntity.status(HttpStatus.OK)
                        .body("Requisição editada com sucesso!");
            }
            logger.error("Falha ao salvar requisição atualizada no repositório");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Falha ao editar a Requisição.");
        } catch (Exception e) {
            logger.error("Erro ao atualizar requisição com ID {}: {}", requisicaoExame.getId(), e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Erro ao atualizar requisição: " + e.getMessage());
        }
    }

    public ResponseEntity<String> deleteRequisicao(long id) {
        logger.info("Excluindo requisição com ID: {}", id);
        try {
            if (!repo.existsById(id)) {
                logger.warn("Requisição com ID {} não encontrada", id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Requisição não encontrada!");
            }
            repo.deleteById(id);
            logger.info("Requisição com ID {} excluída com sucesso", id);
            return ResponseEntity.status(HttpStatus.OK)
                    .body("Requisição deletada com sucesso!");
        } catch (Exception e) {
            logger.error("Erro ao excluir requisição com ID {}: {}", id, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao excluir a Requisição: " + e.getMessage());
        }
    }
}