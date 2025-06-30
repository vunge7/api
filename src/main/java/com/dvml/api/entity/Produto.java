package com.dvml.api.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "product_type", length = 200)
    @NotNull(message = "O tipo de produto é obrigatório.")
    @Size(min = 1, max = 200, message = "O tipo de produto deve ter exatamente 1 caractere.")
    private String productType;

    @Column(name = "product_code", length = 60)
    @NotNull(message = "O código do produto é obrigatório.")
    @Size(min = 1, max = 60, message = "O código do produto deve ter entre 3 e 60 caracteres.")
    @Pattern(regexp = "^[A-Za-z0-9]+$", message = "O código do produto deve ser alfanumérico.")
    private String productCode;

    @Column(name = "product_group", length = 200)
    @NotNull(message = "O grupo do produto é obrigatório.")
    @Size(min = 1, max = 200, message = "O grupo do produto deve ter entre 3 e 50 caracteres.")
    private String productGroup;

    @Column(name = "product_description", length = 200)
    @NotNull(message = "A descrição do produto é obrigatória.")
    @Size(min = 3, max = 200, message = "A descrição do produto deve ter entre 3 e 200 caracteres.")
    private String productDescription;

    @Column(name = "product_number_code", length = 60)
    @NotNull(message = "O número do código do produto é obrigatório.")
    @Size(min = 1, max = 60, message = "O número do código do produto deve ter entre 3 e 60 caracteres.")
    private String productNumberCode;

    @Column(name = "preco", nullable = false)
    @NotNull(message = "O preço do produto é obrigatório.")
    @DecimalMin(value = "0.01", inclusive = true, message = "O preço deve ser maior que zero.")
    private BigDecimal preco;
    @Column(name = "status", nullable = false, columnDefinition = "BOOLEAN DEFAULT TRUE")
    private Boolean status = true;

    @Column(name = "tax_iva", nullable = false)
    @NotNull(message = "a taxa do produto é obrigatório.")
    @DecimalMin(value = "0.00", inclusive = true, message = "O preço deve ser maior que zero.")
    private BigDecimal taxIva;

    @ManyToOne
    @JoinColumn(name = "product_group_id")
    private ProductGroup productGroupId;
}
