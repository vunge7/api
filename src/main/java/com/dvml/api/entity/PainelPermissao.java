package com.dvml.api.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "painel_permissao")
@Getter
@Setter
@NoArgsConstructor
public class PainelPermissao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "data_criacao", nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dataCriacao;

    @Column(name = "data_actualizacao")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dataActualizacao;

    @Column(name = "usuario_id_criacao")
    private Long usuarioIdCriacao;

    @Column(name = "usuario_id_actualizacao")
    private Long usuarioIdActualizacao;

    @Column(name = "painel_id")
    private Long painelId;

    @Column(name = "usuario_id")
    private Long usuarioId;

    @Column(name = "empresa_id")
    @NotNull(message = "empresa é obrigatória")
    private Long empresaId;
}
