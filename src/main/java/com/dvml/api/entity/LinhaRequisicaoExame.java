package com.dvml.api.entity;

import com.dvml.api.util.EstadoRequisicao;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class LinhaRequisicaoExame {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "produto_id")
    private long produtoId;
    @Column(name = "exame", length = 200)
    private String exame;
    @Enumerated(EnumType.STRING)
    @Column(name = "estado")
    private EstadoRequisicao estado;
    @Column(name = "hora", nullable = false)
    private LocalDateTime hora;
    @Column(name = "requisicao_exame_id")
    private long requisicaoExameId;

    @Column(name = "inscricao_id")
    private long inscricaoId;

    @Column(name = "status", nullable = false, columnDefinition = "BOOLEAN DEFAULT TRUE")
    private Boolean status = true;
}
