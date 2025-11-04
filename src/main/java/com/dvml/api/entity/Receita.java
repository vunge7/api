package com.dvml.api.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Receita{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "data")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date data;


    @Column(name = "inscricao_id")
    private Long inscricaoId;
    @Column(name = "usuario_id")
    private Long usuarioId;

    @Column(name = "inicio_tratamento")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date inicioTratamento;

    @Column(name = "fim_tratamento")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date fimTratamento;

    @Column(name = "empresa_id")
    @NotNull(message = "empresa é obrigatória")
    private Long empresaId;

    @Column(name = "status", nullable = false, columnDefinition = "BOOLEAN DEFAULT TRUE")
    private Boolean status = true;

}
