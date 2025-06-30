package com.dvml.api.dto;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
@Data
@Getter
@Setter
@NoArgsConstructor
public class ProdutoDTO {
    private long id;
    private String productType;
    private String productCode;
    private String productGroup;
    private String productDescription;
    private String productNumberCode;
    private BigDecimal preco;
    private BigDecimal taxIva;
}
