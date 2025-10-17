package com.dvml.api.entity;

import com.dvml.api.util.EstadoRequisicao;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class LinhaRequisicaoExame {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull(message = "ID é obrigatório")
    private Long id;

    @Column(name = "produto_id")
    @NotNull(message = "Produto ID é obrigatório")
    private long produtoId;

    @Column(name = "exame", length = 200)
    @NotBlank(message = "Exame é obrigatório")
    @Size(min = 1, max = 200, message = "Exame deve ter entre 1 e 200 caracteres")
    private String exame;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado")
    @NotNull(message = "Estado é obrigatório")
    private EstadoRequisicao estado;

    @Column(name = "hora", nullable = false)
    @NotNull(message = "Hora é obrigatória")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime hora;

    @Column(name = "requisicao_exame_id")
    @NotNull(message = "Requisição Exame ID é obrigatório")
    private long requisicaoExameId;

    @Column(name = "empresa_id")
    @NotNull(message = "Empresa ID é obrigatório")
    private Long empresaId;

    @Column(name = "status", nullable = false, columnDefinition = "BOOLEAN DEFAULT TRUE")
    private Boolean status = true;

    @Column(name = "finalizado", nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean finalizado = false;
}