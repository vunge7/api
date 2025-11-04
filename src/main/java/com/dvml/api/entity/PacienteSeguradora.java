package com.dvml.api.entity;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Date;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class PacienteSeguradora {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "data_criacao")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date dataCricao;
    @Column(name = "data_actualizacao")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date dataActualizacao;
    @Column(name = "usuario_id_criacao")
    private Long usuarioIdCricao;
    @Column(name = "usuario_id_atualizacao")
    private Long usuarioIdAtualizacao;
    @Column(name = "seguradora_id")
    private Long seguradoraId;
    @Column(name = "paciente_id")
    private Long pacienteId;

    @Column(name = "empresa_id")
    @NotNull(message = "empresa é obrigatória")
    private Long empresaId;
}
