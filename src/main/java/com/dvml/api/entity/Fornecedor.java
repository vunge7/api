package com.dvml.api.entity;

import com.dvml.api.util.EstadoFornecedor;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "fornecedor")
public class Fornecedor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Nome é obrigatório")
    @Size(min = 3, max = 200, message = "Nome deve ter entre 3 e 200 caracteres")
    @Column(name = "nome", nullable = false, length = 200)
    private String nome;

    @NotBlank(message = "Contacto é obrigatório")
    @Pattern(regexp = "^\\+?[0-9]{9,15}$|^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$",
            message = "Contacto deve ser um telefone válido (9-15 dígitos) ou email válido")
    @Column(name = "contacto", nullable = false, length = 100)
    private String contacto;

    @NotBlank(message = "NIF é obrigatório")
    @Size(min = 9, max = 20, message = "NIF deve ter entre 9 e 20 caracteres")
    @Column(name = "nif", nullable = false, unique = true, length = 20)
    private String nif;

    @NotBlank(message = "Endereço é obrigatório")
    @Size(min = 5, max = 300, message = "Endereço deve ter entre 5 e 300 caracteres")
    @Column(name = "endereco", nullable = false, length = 300)
    private String endereco;

    @Enumerated(EnumType.STRING)
    @Column(name = "regime_tributario", nullable = false)
    private RegimeTributario regimeTributario;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado_do_fornecedor", nullable = false)
    private EstadoFornecedor estadoFornecedor = EstadoFornecedor.ATIVO;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "data_criacao", nullable = false)
    private Date dataCriacao = new Date();

    public enum RegimeTributario {
        GERAL, SIMPLIFICADO, EXCLUSAO
    }
}