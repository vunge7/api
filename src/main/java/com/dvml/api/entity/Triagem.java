package com.dvml.api.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class Triagem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  long id;
    @Column(name = "data_criacao")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date dataCriacao;

    @Column(name = "fk_inscricao")
    private long fkInscricao;
    @Column(name = "fk_user")
    private long fkUser;

    @OneToMany(mappedBy = "triagem" , cascade = CascadeType.ALL)
    private List<LinhaTriagem> triagens;
}
