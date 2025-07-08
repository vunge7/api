package com.dvml.api.entity;

import com.dvml.api.util.EstadoFuncionario;
import com.dvml.api.util.FechoPeriodo;
import com.dvml.api.util.SegurancaSocial;
import com.dvml.api.util.TipoContrato;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "funcionario")
@Getter
@Setter
public class Funcionario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "pessoa_id")
    private Long pessoaId;

    @Column(name = "tipoContrato", nullable = false)
    @NotNull(message = "O tipo de contrato é obrigatório.")
    private TipoContrato tipoContrato;

    @Column(precision = 12, scale = 2)
    @NotNull(message = "O salário é obrigatório.")
    @DecimalMin(value = "0.0", inclusive = true, message = "O salário deve ser maior ou igual a zero.")
    private BigDecimal salario;

    @Column(name = "data_admissao", nullable = false)
    @NotNull(message = "A data de admissão é obrigatória.")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date dataAdmissao;

    @Column(name = "descricao", nullable = false)
    @NotNull(message = "A descrição é obrigatória.")
    private String descricao;

    @Column(name = "cargo", nullable = false)
    @NotNull(message = "A descrição é obrigatória.")
    private String cargo;

    @Column(name = "departamento_id", nullable = false)
    @NotNull(message = "A descrição é obrigatória.")
    private Long departamentoId;

    @Column(name = "fecho_periodo", nullable = false)
    @NotNull(message = "Fecho de conta é obrigatório.")
    private FechoPeriodo fechoPeriodo;

    @Column(name = "seguranca_social", nullable = false)
    @NotNull(message = "Fecho de conta é obrigatório.")
    private SegurancaSocial segurancaSocial;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "O estado do funcionário é obrigatório.")
    private EstadoFuncionario estadoFuncionario;
}