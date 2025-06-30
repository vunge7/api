package com.dvml.api.dto;

import com.dvml.api.util.EstadoFuncionario;
import com.fasterxml.jackson.annotation.JsonFormat;
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

    @NotNull(message = "PessoaId é obrigatório")
    private Long pessoaId;

    @NotNull(message = "Tipo de contrato é obrigatório")
    private String tipoDeContrato;

    @NotNull(message = "Salário é obrigatório")
    private BigDecimal salario;

    @NotNull(message = "Data de admissão é obrigatória")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date dataAdmissao;

    private String descricao;

    // Removido @NotNull para aceitar string vazia
    private String fechoContas;

    private EstadoFuncionario estadoFuncionario;

    private List<LinhaSubsidioDTO> subsidios;
}