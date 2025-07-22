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
    @Size(min = 1, max = 200, message = "O tipo de produto deve ter entre 1 e 200 caracteres.")
    private String productType;

    @Column(name = "product_code", length = 60)
    @NotNull(message = "O código do produto é obrigatório.")
    @Size(min = 1, max = 60, message = "O código do produto deve ter entre 1 e 60 caracteres.")
    @Pattern(regexp = "^[A-Za-z0-9]+$", message = "O código do produto deve ser alfanumérico.")
    private String productCode;

    @Column(name = "product_group", length = 200)
    @NotNull(message = "O grupo do produto é obrigatório.")
    @Size(min = 1, max = 200, message = "O grupo do produto deve ter entre 1 e 200 caracteres.")
    private String productGroup;

    @Column(name = "product_description", length = 200)
    @NotNull(message = "A descrição do produto é obrigatória.")
    @Size(min = 3, max = 200, message = "A descrição do produto deve ter entre 3 e 200 caracteres.")
    private String productDescription;

    @Column(name = "unidade_medida", length = 200)
    @NotNull(message = "A unidade do produto é obrigatória.")
    private String unidadeMedida;

    @Column(name = "preco", nullable = false)
    @NotNull(message = "O preço do produto é obrigatório.")
    @DecimalMin(value = "0.01", inclusive = true, message = "O preço deve ser maior que zero.")
    private BigDecimal preco;

    @Column(name = "status", nullable = false, columnDefinition = "BOOLEAN DEFAULT TRUE")
    private Boolean status = true;

    @Column(name = "tax_iva", nullable = false)
    @NotNull(message = "A taxa do produto é obrigatória.")
    @DecimalMin(value = "0.00", inclusive = true, message = "A taxa deve ser maior ou igual a zero.")
    private BigDecimal taxIva;

    @Column(name = "final_price", nullable = false)
    @NotNull(message = "O preço final é obrigatório.")
    @DecimalMin(value = "0.00", inclusive = true, message = "O preço final deve ser maior ou igual a zero.")
    private BigDecimal finalPrice;

    @Column(name = "product_group_id")
    @NotNull(message = "Product Group Id é obrigatório.")
    private long productGroupId;

    @Column(name = "produto_pai_id")
    @NotNull(message = "Product Group Id é obrigatório.")
    private long produtoPaiId;

    @Column(name = "unidade_medida_id")
    @NotNull(message = "Unidade de Medida Id é obrigatório.")
    private long unidadeMedidaId;

    @Column(name = "imagem", length = 255)
    private String imagem;
}