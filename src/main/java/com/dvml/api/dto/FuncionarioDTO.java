package com.dvml.api.dto;

import com.dvml.api.util.EstadoFuncionario;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class FuncionarioDTO {

    private Long id;

    @NotNull(message = "PessoaId é obrigatório.")
    private Long pessoaId;

    @NotEmpty(message = "Tipo de contrato é obrigatório.")
    private String tipoDeContrato;

    @NotNull(message = "Salário é obrigatório.")
    @DecimalMin(value = "0.0", inclusive = true, message = "Salário deve ser maior ou igual a zero.")
    private BigDecimal salario;

    @NotNull(message = "Data de admissão é obrigatória.")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date dataAdmissao;

    @NotEmpty(message = "Descrição é obrigatória.")
    private String descricao;

    @NotEmpty(message = "Cargo é obrigatório.")
    private String cargo;

    @NotNull(message = "DepartamentoId é obrigatório.")
    private Long departamentoId;

    @NotEmpty(message = "Fecho de período é obrigatório.")
    private String fechoContas;

    @NotEmpty(message = "Segurança social é obrigatória.")
    private String segurancaSocial;

    @NotNull(message = "Estado do funcionário é obrigatório.")
    private EstadoFuncionario estadoFuncionario;

    private List<LinhaSubsidioDTO> subsidios;
}