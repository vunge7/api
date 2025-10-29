package com.dvml.api.entity;

import com.dvml.api.util.TipoEmpresa;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "empresa")
@Getter
@Setter
@NoArgsConstructor
public class Empresa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String nome;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private TipoEmpresa tipo;

    @Column(nullable = false)
    private boolean status = true;

    @Column(name = "empresa_matriz_id")
    private Long empresaMatrizId; // null = matriz

    @Column(name = "nif", length = 20)
    private String nif;

    @Column(name = "email", length = 100)
    private String email;

    @Column(name = "telefone", length = 30)
    private String telefone;

    @Column(name = "endereco", length = 200)
    private String endereco;


    @Column(name = "seguradora_id")
    private Long seguradoraId; // Long (nullable)
}
