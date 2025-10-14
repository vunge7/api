package com.dvml.api.dto;

import com.dvml.api.util.TipoEmpresa;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmpresaDTO {

    private Long id;
    private String nome;
    private TipoEmpresa tipo;
    private Long empresaMatrizId; // null ou 0 = matriz
    private String nif;
    private String email;
    private String telefone;
    private String endereco;
    private String seguradoraId; // Mantido
}
