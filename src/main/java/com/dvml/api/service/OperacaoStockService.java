package com.dvml.api.service;

import com.dvml.api.dto.LinhaOperacaoStockDTO;
import com.dvml.api.dto.OperacaoStockDTO;
import com.dvml.api.entity.LinhaOperacaoStock;
import com.dvml.api.entity.OperacaoStock;
import com.dvml.api.repository.OperacaoStockRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OperacaoStockService {

    private static final Logger logger = LoggerFactory.getLogger(OperacaoStockService.class);

    @Autowired
    private OperacaoStockRepository operacaoStockRepository;

    @Autowired
    private LinhaOperacaoStockService linhaOperacaoStockService;

    public List<OperacaoStock> listarTodasOperacaoStock() {
        logger.info("Listando todas as operações de estoque");
        return operacaoStockRepository.findAll();
    }

    public OperacaoStock getOperacaoStockById(Long id) {
        logger.info("Buscando operação de estoque com ID: {}", id);
        return operacaoStockRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Operação não encontrada com ID: " + id));
    }

    @Transactional
    public OperacaoStock criar(OperacaoStock operacao) {
        logger.info("Criando nova operação de estoque: {}", operacao);
        validateOperacao(operacao);
        return operacaoStockRepository.save(operacao);
    }

    @Transactional
    public OperacaoStock criarComLinhas(OperacaoStockDTO dto) {
        logger.info("Criando nova operação de estoque com linhas: {}", dto);
        OperacaoStock operacao = new OperacaoStock();
        operacao.setId(dto.getId());
        operacao.setDataOperacao(dto.getDataOperacao());
        operacao.setTipoOperacao(dto.getTipoOperacao());
        operacao.setUsuarioId(dto.getUsuarioId());
        operacao.setArmazemId(dto.getArmazemId());
        operacao.setDescricao(dto.getDescricao());

        validateOperacao(operacao);
        OperacaoStock novaOperacao = operacaoStockRepository.save(operacao);

        // Processar linhas
        for (LinhaOperacaoStockDTO linhaDTO : dto.getLinhas()) {
            logger.info("Processando linha: {}", linhaDTO);
            LinhaOperacaoStock linha = linhaDTO.toEntity(novaOperacao);
            linhaOperacaoStockService.criar(linha);
        }

        return novaOperacao;
    }

    @Transactional
    public OperacaoStock update(OperacaoStock operacao) {
        logger.info("Atualizando operação de estoque com ID: {}", operacao.getId());
        if (!operacaoStockRepository.existsById(operacao.getId())) {
            throw new RuntimeException("Operação não encontrada para atualização com ID: " + operacao.getId());
        }
        validateOperacao(operacao);
        return operacaoStockRepository.save(operacao);
    }

    @Transactional
    public void deleteOperacaoStock(Long id) {
        logger.info("Excluindo operação de estoque com ID: {}", id);
        if (!operacaoStockRepository.existsById(id)) {
            throw new RuntimeException("Operação não encontrada para exclusão com ID: " + id);
        }
        operacaoStockRepository.deleteById(id);
    }

    private void validateOperacao(OperacaoStock operacao) {
        logger.debug("Validando operação: {}", operacao);
        if (operacao.getTipoOperacao() == null) {
            throw new IllegalArgumentException("Tipo de operação é obrigatório");
        }
        if (operacao.getArmazemId() == null) {
            throw new IllegalArgumentException("Armazém é obrigatório");
        }
        if (operacao.getUsuarioId() == null) {
            throw new IllegalArgumentException("Usuário é obrigatório");
        }
        if (operacao.getDataOperacao() == null) {
            throw new IllegalArgumentException("Data da operação é obrigatória");
        }
        if (operacao.getDescricao() == null || operacao.getDescricao().trim().isEmpty()) {
            throw new IllegalArgumentException("Descrição não pode ser vazia");
        }
    }
}