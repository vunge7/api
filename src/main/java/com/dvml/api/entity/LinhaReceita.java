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
public class LinhaReceita{


    /**
     * id: 1,
     * dosagem: "34",
     * medicamento: "Gota Espessa",
     * posologia: "asdfakhsdhfjkashdfkhakjsdhfj",
     * qtd: "837",
     * viaAdministracao: "Retal"
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "medicamento", length = 200)
    @NotNull(message = "obrigatório.")
    private String medicamento;
    @Column(name = "dosagem", length = 200)
    @NotNull(message = "obrigatório.")
    private String dosagem;
    @Column(name = "via_administracao", length = 200)
    @NotNull(message = "obrigatório.")
    private String viaAdministracao;

    @Column(name = "quantidade", length = 150)
    @NotNull(message = "Qtd. obrigatório")
    private String quantidade;

    @Column(name = "posologia", length = 200)
    private String posologia;

    @Column(name = "frequencia", length = 200)
    private String frequencia;
    @Column(name = "receita_id")
    private long receitaId;

    @Column(name = "status", nullable = false, columnDefinition = "BOOLEAN DEFAULT TRUE")
    private Boolean status = true;


}
