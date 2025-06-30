package com.dvml.api.dto;

import com.dvml.api.util.TipoOperacao;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class OperacaoStockDTO {

    private Long id;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @NotNull(message = "Data da operação é obrigatória")
    private Date dataOperacao;

    @NotNull(message = "Tipo de operação é obrigatório")
    private TipoOperacao tipoOperacao;

    @NotNull(message = "Usuário é obrigatório")
    private Long usuarioId;

    @NotNull(message = "Armazém é obrigatório")
    private Long armazemId;

    @Size(max = 500, message = "Descrição deve ter no máximo 500 caracteres")
    private String descricao;

    private List<LinhaOperacaoStockDTO> linhas = new ArrayList<>();
}