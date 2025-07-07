package com.dvml.api.dto;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;


@Data
@Setter
@Getter
public class RequisicaoExameDTO {

    private long id;
    private String medico;
    private String paciente;
    private Date data;
}
