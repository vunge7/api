package com.dvml.api.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public class FuncionarioComSubsidiosDTO {

    @NotNull(message = "funcionário é obrigatório")
    @Valid
    private FuncionarioDTO funcionario;

    @Valid
    private List<LinhaSubsidioDTO> subsidios;

    // Getters e Setters
    public FuncionarioDTO getFuncionario() { return funcionario; }
    public void setFuncionario(FuncionarioDTO funcionario) { this.funcionario = funcionario; }
    public List<LinhaSubsidioDTO> getSubsidios() { return subsidios; }
    public void setSubsidios(List<LinhaSubsidioDTO> subsidios) { this.subsidios = subsidios; }

    @Override
    public String toString() {
        return "FuncionarioComSubsidiosDTO{funcionario=" + funcionario + ", subsidios=" + subsidios + "}";
    }
}