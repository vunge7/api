package com.dvml.api.dto;

import com.dvml.api.entity.Fornecedor.RegimeTributario;
import com.dvml.api.util.EstadoFornecedor;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FornecedorDTO {

    private Long id;
    private String nome;
    @NotBlank(message = "Contacto é obrigatório")
    @Pattern(regexp = "^\\+?[0-9]{9,15}$|^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$",
            message = "Contacto deve ser um telefone válido (9-15 dígitos) ou email válido")
    private String contacto;
    @NotBlank(message = "NIF é obrigatório")
    @Size(min = 9, max = 20, message = "NIF deve ter entre 9 e 20 caracteres")
    private String nif;
    private String endereco;
    private RegimeTributario regimeTributario;
    private EstadoFornecedor estadoFornecedor;
}