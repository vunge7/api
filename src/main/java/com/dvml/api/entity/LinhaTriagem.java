package com.dvml.api.entity;

import com.dvml.api.util.Campo;
import com.dvml.api.util.TipoUsuario;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class LinhaTriagem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "campo")
    private Campo campo;

    @Column(name = "valor")
    private String valor;

    @Column(name = "unidade")
    private String unidade;

    @Column(name = "triagem_id")
   private Long triagemId;

    @Column(name = "empresa_id")
    @NotNull(message = "empresa é obrigatória")
    private Long empresaId;
}
