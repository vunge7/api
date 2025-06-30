package com.dvml.api.dto;

import com.dvml.api.entity.Inscricao;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;


@Data
@Getter
@Setter
public class PacienteDTO {
    private long id;
    private String nome;
    private String apelido;
    private String nif;
    private String bairro;
    private String habilitacao;
    private String profissao;
    private String estadoCivil;
    private String nacionalidade;
    private String raca;
    private String genero;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date dataNascimento;
    private String pai;
    private String mae;
    private String paisEndereco;
    private String provinciaEndereco;
    private String municipioEndereco;
    private String paisNascimento;
    private String provinciaNascimento;
    private String municipioNascimento;
    private String localNascimento;

    private String endereco;
    private String nomePhoto;
    private long pessoaId;

}
