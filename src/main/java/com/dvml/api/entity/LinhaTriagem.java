package com.dvml.api.entity;

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
    @Column(name = "campo", nullable = false, length = 75)
    private String campo;
    @Column(name = "valor", nullable = false, length = 75)
    private String valor;
    @Column(name = "unidade", nullable = false, length = 50)
    private String unidade;

    @Column(name = "triagem_id")
   private long triagemId;
}
