package com.dvml.api.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Paciente {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "data_cadastro")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date dataCadastro;

    @Column(name = "data_actualizacao")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date dataActualizacao;

    @Column(name = "pessoa_id")
    private Long pessoaId;

    @Column(name = "empresa_id")
    @NotNull(message = "empresa é obrigatória")
    private Long empresaId;
}
