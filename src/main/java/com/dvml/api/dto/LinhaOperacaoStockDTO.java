package com.dvml.api.dto;

import com.dvml.api.entity.LinhaOperacaoStock;
import com.dvml.api.entity.OperacaoStock;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class LinhaOperacaoStockDTO {

    private Long id;

    @NotNull(message = "Armazém de origem é obrigatório")
    private Long armazemIdOrigem;

    @NotNull(message = "Lote de origem é obrigatório")
    private Long loteIdOrigem;

    private Long produtoId; // Pode ser nulo se designacaoProduto for fornecido

    private String designacaoProduto; // Novo campo para o nome do produto

    @NotNull(message = "Quantidade anterior é obrigatória")
    @DecimalMin(value = "0.0", inclusive = true, message = "Quantidade anterior deve ser maior ou igual a zero")
    private BigDecimal qtdAnterior;

    @NotNull(message = "Quantidade da operação é obrigatória")
    @DecimalMin(value = "0.0", inclusive = true, message = "Quantidade da operação deve ser maior ou igual a zero")
    private BigDecimal qtdOperacao;

    @NotNull(message = "Quantidade atual é obrigatória")
    @DecimalMin(value = "0.0", inclusive = true, message = "Quantidade atual deve ser maior ou igual a zero")
    private BigDecimal qtdActual;

    private Long armazemIdDestino;

    private Long loteIdDestino;

    @NotNull(message = "Operação de stock é obrigatória")
    private Long operacaoStockId;

    public LinhaOperacaoStock toEntity(OperacaoStock operacaoStock) {
        LinhaOperacaoStock entity = new LinhaOperacaoStock();
        entity.setId(this.id);
        entity.setArmazemIdOrigem(this.armazemIdOrigem);
        entity.setLoteIdOrigem(this.loteIdOrigem);
        entity.setProdutoId(this.produtoId); // Será preenchido no controlador ou serviço
        entity.setQtdAnterior(this.qtdAnterior);
        entity.setQtdOperacao(this.qtdOperacao);
        entity.setQtdActual(this.qtdActual);
        entity.setArmazemIdDestino(this.armazemIdDestino);
        entity.setLoteIdDestino(this.loteIdDestino);
        entity.setOperacaoStock(operacaoStock);
        return entity;
    }
}