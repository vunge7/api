package com.dvml.api.entity;

import com.dvml.api.util.TipoOperacao;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "operacao_stock")
@Getter
@Setter
@NoArgsConstructor
@ToString(exclude = "linhas")
public class OperacaoStock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "data_operacao")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    @NotNull(message = "Data da operação é obrigatória")
    private Date dataOperacao;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_operacao")
    @NotNull(message = "Tipo de operação é obrigatório")
    private TipoOperacao tipoOperacao;

    @Column(name = "usuario_id", nullable = false)
    @NotNull(message = "Usuário é obrigatório")
    private Long usuarioId;

    @Column(name = "armazem_id", nullable = false)
    @NotNull(message = "Armazém é obrigatório")
    private Long armazemId;

    @Column(name = "descricao")
    @Size(max = 500, message = "Descrição deve ter no máximo 500 caracteres")
    private String descricao;

    @OneToMany(mappedBy = "operacaoStock", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<LinhaOperacaoStock> linhas = new ArrayList<>();
}