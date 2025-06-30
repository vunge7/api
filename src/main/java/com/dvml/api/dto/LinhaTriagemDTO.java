package com.dvml.api.dto;

import lombok.Data;

@Data
public class LinhaTriagemDTO {
    private String campo;
    private String valor;
    private String unidade;
    private long triagemId;
}
