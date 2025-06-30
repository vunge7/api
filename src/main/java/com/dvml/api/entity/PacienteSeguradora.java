package com.dvml.api.entity;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
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
    private long id;

    @Column(name = "data_criacao")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date dataCricao;
    @Column(name = "data_actualizacao")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date dataActualizacao;
    @Column(name = "usuario_id_criacao")
    private long usuarioIdCricao;
    @Column(name = "usuario_id_atualizacao")
    private long usuarioIdAtualizacao;
    @Column(name = "seguradora_id")
    private long seguradoraId;
    @Column(name = "paciente_id")
    private long pacienteId;
}
