package com.dvml.api.dto;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@NoArgsConstructor
public class SeguradoraDTO {
    private long id;
    private String nome;
    private String telefone;
    private String nif;
}
