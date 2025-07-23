package com.dvml.api.dto;

import com.dvml.api.util.EstadoUsuario;
import com.dvml.api.util.TipoUsuario;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class UsuarioDTO {

    private Long id;

    @NotBlank(message = "O username não pode ser vazio")
    @Size(min = 3, max = 100, message = "O username deve ter entre 3 e 100 caracteres")
    private String userName;

    @NotBlank(message = "A senha não pode ser vazia")
    @Size(min = 8, max = 100, message = "A senha deve ter entre 8 e 100 caracteres")
    private String senha;

    @NotBlank(message = "O número de ordem não pode ser vazio")
    @Size(min = 1, max = 100, message = "O número de ordem deve ter entre 1 e 100 caracteres")
    private String numeroOrdem;

    @NotNull(message = "O estado do usuário não pode ser nulo")
    private EstadoUsuario estadoUsuario;

    @NotNull(message = "O tipo de usuário não pode ser nulo")
    private TipoUsuario tipoUsuario;

    @NotNull(message = "O ID do funcionário não pode ser nulo")
    private Long funcionarioId;

    @NotNull(message = "O ID da função não pode ser nulo")
    private Long funcaoId;

    @Size(max = 100, message = "O IP deve ter no máximo 100 caracteres")
    private String ip;

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getNumeroOrdem() {
        return numeroOrdem;
    }

    public void setNumeroOrdem(String numeroOrdem) {
        this.numeroOrdem = numeroOrdem;
    }

    public EstadoUsuario getEstadoUsuario() {
        return estadoUsuario;
    }

    public void setEstadoUsuario(EstadoUsuario estadoUsuario) {
        this.estadoUsuario = estadoUsuario;
    }

    public TipoUsuario getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(TipoUsuario tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public Long getFuncionarioId() {
        return funcionarioId;
    }

    public void setFuncionarioId(Long funcionarioId) {
        this.funcionarioId = funcionarioId;
    }

    public Long getFuncaoId() {
        return funcaoId;
    }

    public void setFuncaoId(Long funcaoId) {
        this.funcaoId = funcaoId;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}