package com.dvml.api.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LinhasLotesDTO {

    private Long id;
    private Long lotes_id;
    private Long produto_id;
    private Integer quantidade;
}