package com.dvml.api.service;

import com.dvml.api.entity.LinhaRequisicaoExame;
import com.dvml.api.repository.LinhaRequisicaoExameRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class LinhaRequisicaoExameService {

    private static final Logger logger = LoggerFactory.getLogger(LinhaRequisicaoExameService.class);

    @Autowired
    private LinhaRequisicaoExameRepository repo;

    public List<LinhaRequisicaoExame> listarTodas() {
        // Corrigido: faltava espaço e ponto errado
        return repo.findAll();
    }

    public LinhaRequisicaoExame getLinhaRequisicaoById(long id) {
        logger.info("Buscando linha de requisição com ID: {}", id);
        Optional<LinhaRequisicaoExame> exame = repo.findById(id);
        if (exame.isEmpty()) {
            logger.warn("Linha de requisição com ID {} não encontrada", id);
            return null;
        }
        return exame.get();
    }

    public List<LinhaRequisicaoExame> listarTodasLinhaRequisicoes() {
        logger.info("Listando todas as linhas de requisição");
        List<LinhaRequisicaoExame> linhas = repo.findAllOrderByNomeAsc();
        logger.info("Encontradas {} linhas de requisição", linhas.size());
        return linhas;
    }

    public ResponseEntity<String> criar(LinhaRequisicaoExame linhaRequisicaoExame) {
        logger.info("Criando nova linha de requisição: {}", linhaRequisicaoExame);
        try {
            if (linhaRequisicaoExame.getHora() == null) {
                logger.info("Campo 'hora' não fornecido, definindo como data atual");
                linhaRequisicaoExame.setHora(LocalDateTime.now());
            }

            LinhaRequisicaoExame saved = repo.save(linhaRequisicaoExame);
            if (Objects.nonNull(saved)) {
                logger.info("Linha de requisição criada com ID: {}", saved.getId());
                return ResponseEntity.status(HttpStatus.CREATED)
                        .body("Linha Requisição criada com sucesso!");
            }

            logger.error("Falha ao salvar linha de requisição no repositório");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Falha ao criar a Linha Requisição.");

        } catch (Exception e) {
            logger.error("Erro ao criar linha de requisição: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Erro ao criar linha de requisição: " + e.getMessage());
        }
    }

    public ResponseEntity<String> update(LinhaRequisicaoExame linhaRequisicaoExame) {
        logger.info("Atualizando linha de requisição com ID: {}", linhaRequisicaoExame.getId());
        try {
            Optional<LinhaRequisicaoExame> opt = repo.findById(linhaRequisicaoExame.getId());
            if (opt.isEmpty()) {
                logger.warn("Linha de requisição com ID {} não encontrada", linhaRequisicaoExame.getId());
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Linha Requisição não encontrada!");
            }

            LinhaRequisicaoExame linhaRequisicaoToUpdate = opt.get();
            linhaRequisicaoToUpdate.setRequisicaoExameId(linhaRequisicaoExame.getRequisicaoExameId());
            linhaRequisicaoToUpdate.setStatus(linhaRequisicaoExame.getStatus());
            linhaRequisicaoToUpdate.setExame(linhaRequisicaoExame.getExame());
            linhaRequisicaoToUpdate.setHora(linhaRequisicaoExame.getHora());
            linhaRequisicaoToUpdate.setEstado(linhaRequisicaoExame.getEstado());
            linhaRequisicaoToUpdate.setProdutoId(linhaRequisicaoExame.getProdutoId());
            linhaRequisicaoToUpdate.setFinalizado(linhaRequisicaoExame.getFinalizado());
            linhaRequisicaoToUpdate.setEmpresaId(linhaRequisicaoExame.getEmpresaId());

            LinhaRequisicaoExame saved = repo.save(linhaRequisicaoToUpdate);
            if (Objects.nonNull(saved)) {
                logger.info("Linha de requisição com ID {} atualizada com sucesso", saved.getId());
                return ResponseEntity.status(HttpStatus.OK)
                        .body("Linha Requisição editada com sucesso!");
            }

            logger.error("Falha ao salvar linha de requisição atualizada no repositório");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Falha ao editar a Linha Requisição.");

        } catch (Exception e) {
            logger.error("Erro ao atualizar linha de requisição com ID {}: {}", linhaRequisicaoExame.getId(), e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Erro ao atualizar linha de requisição: " + e.getMessage());
        }
    }

    public ResponseEntity<String> deleteLinhaRequisicao(long id) {
        logger.info("Excluindo linha de requisição com ID: {}", id);
        try {
            if (!repo.existsById(id)) {
                logger.warn("Linha de requisição com ID {} não encontrada", id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Linha Requisição não encontrada!");
            }

            repo.deleteById(id);
            logger.info("Linha de requisição com ID {} excluída com sucesso", id);
            return ResponseEntity.status(HttpStatus.OK)
                    .body("Linha Requisição deletada com sucesso!");

        } catch (Exception e) {
            logger.error("Erro ao excluir linha de requisição com ID {}: {}", id, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao excluir a Linha Requisição: " + e.getMessage());
        }
    }

    public List<LinhaRequisicaoExame> listarLinhasPorRequisicaoId(long requisicaoExameId) {
        logger.info("Listando linhas de requisição para requisicaoExameId: {}", requisicaoExameId);
        try {
            List<LinhaRequisicaoExame> linhas = repo.findAllByRequisicaoId(requisicaoExameId);
            logger.info("Encontradas {} linhas para requisicaoExameId: {}", linhas.size(), requisicaoExameId);
            return linhas;
        } catch (Exception e) {
            logger.error("Erro ao listar linhas para requisicaoExameId {}: {}", requisicaoExameId, e.getMessage(), e);
            throw new RuntimeException("Erro ao listar linhas de requisição: " + e.getMessage());
        }
    }
}
