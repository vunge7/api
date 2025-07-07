package com.dvml.api.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tipo_exame")
@Getter
@Setter
@NoArgsConstructor
public class TipoExame {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Nome do tipo de exame é obrigatório")
    @Size(min = 3, max = 100, message = "Nome do tipo de exame deve ter entre 3 e 100 caracteres")
    @Column(name = "nome", nullable = false, unique = true)
    private String nome;

    @Size(max = 500, message = "Descrição deve ter no máximo 500 caracteres")
    @Column(name = "descricao")
    private String descricao;

    @NotBlank(message = "Tipo de amostra é obrigatório")
    @Size(max = 50, message = "Tipo de amostra deve ter no máximo 50 caracteres")
    @Column(name = "tipo_amostra", nullable = false)
    private String tipoAmostra;
}