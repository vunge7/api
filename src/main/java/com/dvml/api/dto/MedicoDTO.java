package com.dvml.api.dto;

import com.dvml.api.util.Genero;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
public class MedicoDTO {
    private Long id; // Adicionado para identificar o médico
    private String nome;
    private String numeroOrdem;
    private String userName;
    private String telefone;
    private String email;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date dataNascimento;
    private Date dataAdmissao;
    private String tipoDeContrato;
    private BigDecimal salario;
    private BigDecimal subsidios;
    private String descricao;
    private String fechoDeContas;
    private String endereco;
    @Enumerated(EnumType.STRING)
    private Genero genero;
    private Long usuarioId; // Referência por ID
    private Long funcionarioId; // Referência por ID

    public MedicoDTO(Long id, String nome, String numeroOrdem, String userName, String telefone, String email, Date dataNascimento, Date dataAdmissao, String tipoDeContrato, BigDecimal salario, BigDecimal subsidios, String descricao, String fechoDeContas, String endereco, Genero genero, Long usuarioId, Long funcionarioId) {
        this.id = id;
        this.nome = nome;
        this.numeroOrdem = numeroOrdem;
        this.userName = userName;
        this.telefone = telefone;
        this.email = email;
        this.dataNascimento = dataNascimento;
        this.dataAdmissao = dataAdmissao;
        this.tipoDeContrato = tipoDeContrato;
        this.salario = salario;
        this.subsidios = subsidios;
        this.descricao = descricao;
        this.fechoDeContas = fechoDeContas;
        this.endereco = endereco;
        this.genero = genero;
        this.usuarioId = usuarioId;
        this.funcionarioId = funcionarioId;
    }
}