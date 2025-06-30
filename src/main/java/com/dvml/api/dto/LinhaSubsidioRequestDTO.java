package com.dvml.api.dto;

import java.util.List;

public class LinhaSubsidioRequestDTO {
    private List<LinhaSubsidioDTO> subsidios;

    public List<LinhaSubsidioDTO> getSubsidios() { return subsidios; }
    public void setSubsidios(List<LinhaSubsidioDTO> subsidios) { this.subsidios = subsidios; }
}