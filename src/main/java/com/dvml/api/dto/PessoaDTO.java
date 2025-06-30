package com.dvml.api.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;


public class PessoaDTO {
    private Long id;
    private String nome;
    private String nif;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date dataNascimento;
    private String telefone;
    private String email;
    private String endereco;
    private String genero;

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getNif() { return nif; }
    public void setNif(String nif) { this.nif = nif; }
    public Date getDataNascimento() { return dataNascimento; }

    public void setDataNascimento(Date dataNascimento) { this.dataNascimento = dataNascimento; }
    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getEndereco() { return endereco; }
    public void setEndereco(String endereco) { this.endereco = endereco; }
    public String getGenero() { return genero; }
    public void setGenero(String genero) { this.genero = genero; }

    @Override
    public String toString() {
        return "PessoaDTO{id=" + id + ", nome='" + nome + "', nif='" + nif + "', dataNascimento=" + dataNascimento + "}";
    }
}