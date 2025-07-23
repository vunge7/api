package com.dvml.api.entity;

import com.dvml.api.util.EstadoUsuario;
import com.dvml.api.util.TipoUsuario;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "data_cadastro")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date dataCadastro;

    @Column(name = "data_atualizacao")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date dataAtualizacao;

    @Column(name = "user_name", nullable = false, length = 100)
    @NotBlank(message = "O username não pode ser vazio ou nulo")
    private String userName;

    @Column(name = "senha", nullable = false, length = 100)
    @NotBlank(message = "A senha não pode ser vazia ou nula")
    private String senha;

    @Column(name = "numero_ordem", nullable = false, length = 100)
    @NotBlank(message = "O número de ordem não pode ser vazio ou nulo")
    private String numeroOrdem;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado")
    @NotNull(message = "O estado do usuário não pode ser nulo")
    private EstadoUsuario estadoUsuario;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_usuario")
    @NotNull(message = "O tipo de usuário não pode ser nulo")
    private TipoUsuario tipoUsuario;

    @Column(name = "funcao_id")
    private long funcaoId;

    @Column(name = "funcionario_id", nullable = false, length = 100)
    @NotNull(message = "O ID do funcionário não pode ser nulo")
    private long funcionarioId;

    @Column(name = "ip", nullable = false, length = 100)
    @NotBlank(message = "O IP não pode ser vazio ou nulo")
    private String ip;

    @Column(name = "usuario_id", nullable = false, length = 100)
    @NotNull(message = "O ID do usuário não pode ser nulo")
    private long usuarioId;

    @Column(name = "status", nullable = false, columnDefinition = "BOOLEAN DEFAULT TRUE")
    private Boolean status = true;
}
