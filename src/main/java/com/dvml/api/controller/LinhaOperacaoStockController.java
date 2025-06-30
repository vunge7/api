package com.dvml.api.controller;

import com.dvml.api.dto.LinhaOperacaoStockDTO;
import com.dvml.api.entity.LinhaOperacaoStock;
import com.dvml.api.entity.OperacaoStock;
import com.dvml.api.entity.ProductGroup;
import com.dvml.api.service.LinhaOperacaoStockService;
import com.dvml.api.service.OperacaoStockService;
import com.dvml.api.service.ProductGroupService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/linha-operacao-stock")
public class LinhaOperacaoStockController {

    private static final Logger logger = LoggerFactory.getLogger(LinhaOperacaoStockController.class);

    @Autowired
    private LinhaOperacaoStockService service;

    @Autowired
    private ProductGroupService productGroupService;

    @Autowired
    private OperacaoStockService operacaoStockService;

    @GetMapping("/all")
    public ResponseEntity<List<LinhaOperacaoStock>> getAllLinhaOperacaoStock() {
        logger.info("Listando todas as linhas de operação de estoque");
        List<LinhaOperacaoStock> linhas = service.listarTodasLinhaOperacaoStock();
        return ResponseEntity.ok(linhas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LinhaOperacaoStock> getLinhaOperacaoStockById(@PathVariable Long id) {
        logger.info("Buscando linha de operação de estoque com ID: {}", id);
        try {
            LinhaOperacaoStock linha = service.getLinhaOperacaoStockById(id);
            return ResponseEntity.ok(linha);
        } catch (RuntimeException e) {
            logger.error("Erro ao buscar linha com ID {}: {}", id, e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<Object> adicionar(@RequestBody @Valid LinhaOperacaoStockDTO dto) {
        logger.info("Adicionando linha de operação de estoque: {}", dto);
        try {
            // Valida produtoId
            if (dto.getProdutoId() == null && (dto.getDesignacaoProduto() == null || dto.getDesignacaoProduto().trim().isEmpty())) {
                logger.error("ProdutoId ou designacaoProduto deve ser fornecido");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                        new HashMap<String, String>() {{ put("error", "ProdutoId ou designacaoProduto deve ser fornecido"); }}
                );
            }

            // Resolver designacaoProduto para produtoId, se fornecido
            if (dto.getDesignacaoProduto() != null && !dto.getDesignacaoProduto().trim().isEmpty()) {
                ProductGroup productGroup = productGroupService.findByDesignacaoProduto(dto.getDesignacaoProduto())
                        .orElseThrow(() -> {
                            logger.warn("Produto não encontrado: {}", dto.getDesignacaoProduto());
                            return new RuntimeException("Produto não encontrado: " + dto.getDesignacaoProduto());
                        });
                dto.setProdutoId(productGroup.getId());
            }

            // Valida operacaoStockId
            OperacaoStock operacaoStock = operacaoStockService.getOperacaoStockById(dto.getOperacaoStockId());
            if (operacaoStock == null) {
                logger.error("Operação de estoque não encontrada: {}", dto.getOperacaoStockId());
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                        new HashMap<String, String>() {{ put("error", "Operação de estoque não encontrada: " + dto.getOperacaoStockId()); }}
                );
            }

            // Converter DTO para entidade
            LinhaOperacaoStock linha = dto.toEntity(operacaoStock);

            // Criar a linha de operação
            LinhaOperacaoStock novaLinha = service.criar(linha);
            logger.info("Linha de operação de estoque criada com sucesso: ID {}", novaLinha.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(novaLinha);
        } catch (IllegalArgumentException e) {
            logger.error("Erro de validação: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new HashMap<String, String>() {{ put("error", e.getMessage()); }}
            );
        } catch (RuntimeException e) {
            logger.error("Erro ao criar linha de operação: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new HashMap<String, String>() {{ put("error", e.getMessage()); }}
            );
        } catch (Exception e) {
            logger.error("Erro interno ao criar linha de operação: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new HashMap<String, String>() {{ put("error", "Erro interno: " + e.getMessage()); }}
            );
        }
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<Object> updateLinhaOperacaoStock(@PathVariable Long id, @RequestBody @Valid LinhaOperacaoStockDTO dto) {
        logger.info("Atualizando linha de operação de estoque com ID: {}", id);
        try {
            // Resolver designacaoProduto para produtoId, se fornecido
            if (dto.getDesignacaoProduto() != null && !dto.getDesignacaoProduto().trim().isEmpty()) {
                ProductGroup productGroup = productGroupService.findByDesignacaoProduto(dto.getDesignacaoProduto())
                        .orElseThrow(() -> new RuntimeException("Produto não encontrado: " + dto.getDesignacaoProduto()));
                dto.setProdutoId(productGroup.getId());
            }
            OperacaoStock operacaoStock = operacaoStockService.getOperacaoStockById(dto.getOperacaoStockId());
            if (operacaoStock == null) {
                logger.error("Operação de estoque não encontrada: {}", dto.getOperacaoStockId());
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                        new HashMap<String, String>() {{ put("error", "Operação de estoque não encontrada: " + dto.getOperacaoStockId()); }}
                );
            }
            LinhaOperacaoStock linha = dto.toEntity(operacaoStock);
            linha.setId(id);
            LinhaOperacaoStock atualizada = service.update(linha);
            return ResponseEntity.ok(atualizada);
        } catch (RuntimeException e) {
            logger.error("Erro ao atualizar linha: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new HashMap<String, String>() {{ put("error", e.getMessage()); }}
            );
        } catch (Exception e) {
            logger.error("Erro interno ao atualizar linha: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new HashMap<String, String>() {{ put("error", "Erro interno: " + e.getMessage()); }}
            );
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteLinhaOperacaoStock(@PathVariable Long id) {
        logger.info("Excluindo linha de operação de estoque com ID: {}", id);
        try {
            service.deleteLinhaOperacaoStock(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            logger.error("Erro ao excluir linha: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new HashMap<String, String>() {{ put("error", e.getMessage()); }}
            );
        }
    }

    @GetMapping("/lotes/{loteId}")
    public ResponseEntity<List<LinhaOperacaoStock>> getProdutosByLoteId(@PathVariable Long loteId) {
        logger.info("Buscando produtos para o lote ID: {}", loteId);
        try {
            List<LinhaOperacaoStock> produtos = service.findProdutosByLoteId(loteId);
            return produtos.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(produtos);
        } catch (RuntimeException e) {
            logger.error("Erro ao buscar produtos para lote ID {}: {}", loteId, e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException ex) {
        logger.error("Erro de validação: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new HashMap<String, String>() {{ put("error", ex.getMessage()); }}
        );
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Object> handleRuntimeException(RuntimeException ex) {
        logger.error("Erro de runtime: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new HashMap<String, String>() {{ put("error", ex.getMessage()); }}
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGeneralException(Exception ex) {
        logger.error("Erro interno: {}", ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                new HashMap<String, String>() {{ put("error", "Erro interno: " + ex.getMessage()); }}
        );
    }
}