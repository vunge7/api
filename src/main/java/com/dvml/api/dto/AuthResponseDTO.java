package com.dvml.api.dto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AuthResponseDTO {
    private String token;
    private Long id;
    private String username;
    private String tipo;
    private List<FilialDTO> filiais;
}