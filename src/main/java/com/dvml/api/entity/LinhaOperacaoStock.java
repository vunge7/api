package com.dvml.api.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "linha_operacao_stock")
@Getter
@Setter
@NoArgsConstructor
public class LinhaOperacaoStock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "armazem_id_origem")
    @NotNull(message = "Armazém de origem é obrigatório")
    private Long armazemIdOrigem;

    @Column(name = "lote_id_origem")
    @NotNull(message = "Lote de origem é obrigatório")
    private Long loteIdOrigem;

    @Column(name = "produto_id")
    @NotNull(message = "Produto é obrigatório")
    private Long produtoId;

    @Column(name = "qtd_anterior")
    @NotNull(message = "Quantidade anterior é obrigatória")
    @DecimalMin(value = "0.0", inclusive = true, message = "Quantidade anterior deve ser maior ou igual a zero")
    private BigDecimal qtdAnterior;

    @Column(name = "qtd_operacao")
    @NotNull(message = "Quantidade da operação é obrigatória")
    @DecimalMin(value = "0.0", inclusive = true, message = "Quantidade da operação deve ser maior ou igual a zero")
    private BigDecimal qtdOperacao;

    @Column(name = "qtd_actual")
    @NotNull(message = "Quantidade atual é obrigatória")
    @DecimalMin(value = "0.0", inclusive = true, message = "Quantidade atual deve ser maior ou igual a zero")
    private BigDecimal qtdActual;

    @Column(name = "armazem_id_destino")
    private Long armazemIdDestino;

    @Column(name = "lote_id_destino")
    private Long loteIdDestino;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "operacao_stock_id")
    @NotNull(message = "Operação de stock é obrigatória")
    @JsonBackReference
    private OperacaoStock operacaoStock;
}