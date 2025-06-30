package com.dvml.api.service;

import com.dvml.api.entity.LinhaOperacaoStock;
import com.dvml.api.entity.OperacaoStock;
import com.dvml.api.repository.LinhaOperacaoStockRepository;
import com.dvml.api.util.TipoOperacao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class LinhaOperacaoStockService {

    private static final Logger logger = LoggerFactory.getLogger(LinhaOperacaoStockService.class);

    @Autowired
    private LinhaOperacaoStockRepository linhaOperacaoStockRepository;

    public List<LinhaOperacaoStock> listarTodasLinhaOperacaoStock() {
        logger.info("Listando todas as linhas de operação de estoque");
        return linhaOperacaoStockRepository.findAll();
    }

    public LinhaOperacaoStock getLinhaOperacaoStockById(Long id) {
        logger.info("Buscando linha de operação de estoque com ID: {}", id);
        return linhaOperacaoStockRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Linha de operação não encontrada com ID: " + id));
    }

    @Transactional
    public LinhaOperacaoStock criar(LinhaOperacaoStock linha) {
        logger.info("Criando nova linha de operação de estoque: {}", linha);
        validateLinhaOperacao(linha);
        return linhaOperacaoStockRepository.save(linha);
    }

    @Transactional
    public LinhaOperacaoStock update(LinhaOperacaoStock linha) {
        logger.info("Atualizando linha de operação de estoque com ID: {}", linha.getId());
        if (!linhaOperacaoStockRepository.existsById(linha.getId())) {
            throw new RuntimeException("Linha de operação não encontrada para atualização com ID: " + linha.getId());
        }
        validateLinhaOperacao(linha);
        return linhaOperacaoStockRepository.save(linha);
    }

    @Transactional
    public void deleteLinhaOperacaoStock(Long id) {
        logger.info("Excluindo linha de operação de estoque com ID: {}", id);
        if (!linhaOperacaoStockRepository.existsById(id)) {
            throw new RuntimeException("Linha de operação não encontrada para exclusão com ID: " + id);
        }
        linhaOperacaoStockRepository.deleteById(id);
    }

    public List<LinhaOperacaoStock> findProdutosByLoteId(Long loteId) {
        logger.info("Buscando produtos para o lote ID: {}", loteId);
        List<LinhaOperacaoStock> linhas = linhaOperacaoStockRepository.findByLoteIdOrigem(loteId);
        if (linhas.isEmpty()) {
            logger.warn("Nenhum produto encontrado para o lote ID: {}", loteId);
        }
        return linhas;
    }

    private void validateLinhaOperacao(LinhaOperacaoStock linha) {
        logger.debug("Validando linha de operação: {}", linha);
        if (linha.getArmazemIdOrigem() == null) {
            throw new IllegalArgumentException("Armazém de origem é obrigatório");
        }
        if (linha.getLoteIdOrigem() == null) {
            throw new IllegalArgumentException("Lote de origem é obrigatório");
        }
        if (linha.getProdutoId() == null) {
            throw new IllegalArgumentException("Produto é obrigatório");
        }
        if (linha.getQtdOperacao() == null) {
            throw new IllegalArgumentException("Quantidade da operação é obrigatória");
        }
        if (linha.getQtdAnterior() == null || linha.getQtdAnterior().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Quantidade anterior deve ser maior ou igual a zero");
        }
        if (linha.getQtdActual() == null || linha.getQtdActual().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Quantidade atual deve ser maior ou igual a zero");
        }
        if (linha.getOperacaoStock() == null) {
            throw new IllegalArgumentException("Operação de stock é obrigatória");
        }


        TipoOperacao tipoOperacao = linha.getOperacaoStock().getTipoOperacao();

        if (tipoOperacao == TipoOperacao.TRANSFERENCIA && linha.getArmazemIdDestino() == null) {
            throw new IllegalArgumentException("Armazém de destino é obrigatório para transferências");
        }
        if (tipoOperacao == TipoOperacao.SAIDA || tipoOperacao == TipoOperacao.TRANSFERENCIA) {
            BigDecimal estoqueAtual = getEstoqueAtual(linha.getArmazemIdOrigem(), linha.getLoteIdOrigem(), linha.getProdutoId());
            if (estoqueAtual.compareTo(linha.getQtdOperacao()) < 0) {
                throw new IllegalArgumentException(
                        String.format("Quantidade insuficiente no estoque. Disponível: %s, Solicitado: %s",
                                estoqueAtual, linha.getQtdOperacao()));
            }
        }
        if (tipoOperacao == TipoOperacao.ANULACAO && linha.getQtdOperacao().compareTo(BigDecimal.ZERO) != 0) {
            throw new IllegalArgumentException("Quantidade da operação deve ser zero para anulações");
        }
    }

    private BigDecimal getEstoqueAtual(Long armazemId, Long loteId, Long produtoId) {
        logger.info("Consultando estoque para armazém: {}, lote: {}, produto: {}", armazemId, loteId, produtoId);
        BigDecimal estoque = linhaOperacaoStockRepository.sumQtdActualByArmazemLoteAndProduto(armazemId, loteId, produtoId)
                .orElse(BigDecimal.ZERO);
        logger.info("Estoque atual: {}", estoque);
        return estoque;
    }
}