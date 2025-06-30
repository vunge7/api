package com.dvml.api.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class LinhaGasto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "data_insercao")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date dataInsercao;

    @Column(name = "servico_id")
    private String servicoId;

    @Column(name = "servico_descricao")
    private String servicoDescricao;

    @Column(name = "quantidade")
    private String quantidade;
    @Column(name = "iva")
    private String iva;

    @Column(name = "desconto", nullable = false)
    private BigDecimal desconto;

    @Column(name = "preco", nullable = false, precision = 30, scale = 2)
    private BigDecimal preco;

    @Column(name = "gasto_id")
    private long gastoId;
    @Column(name = "status", nullable = false, columnDefinition = "BOOLEAN DEFAULT TRUE")
    private Boolean status = true;
}
