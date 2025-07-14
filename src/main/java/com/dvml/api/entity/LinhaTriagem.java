package com.dvml.api.entity;

import com.dvml.api.util.Campo;
import com.dvml.api.util.TipoUsuario;
import jakarta.persistence.*;
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
    private long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "campo")
    private Campo campo;

    @Column(name = "valor")
    private String valor;

    @Column(name = "unidade")
    private String unidade;

    @Column(name = "triagem_id")
   private long triagemId;
}
