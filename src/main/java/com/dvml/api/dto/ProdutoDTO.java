package com.dvml.api.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public class ProdutoDTO {

    private Long id;

    @NotNull(message = "O tipo de produto é obrigatório.")
    @Size(min = 1, max = 200, message = "O tipo de produto deve ter entre 1 e 200 caracteres.")
    @Column(name = "product_type")
    private String productType;

    @NotNull(message = "O código do produto é obrigatório.")
    @Size(min = 1, max = 60, message = "O código do produto deve ter entre 1 e 60 caracteres.")
    @Pattern(regexp = "^[A-Za-z0-9]+$", message = "O código do produto deve ser alfanumérico.")
    @Column(name = "product_code")
    private String productCode;

    @NotNull(message = "O grupo do produto é obrigatório.")
    @Size(min = 1, max = 200, message = "O grupo do produto deve ter entre 1 e 200 caracteres.")
    @Column(name = "product_group")
    private String productGroup;

    @NotNull(message = "A descrição do produto é obrigatória.")
    @Size(min = 3, max = 200, message = "A descrição do produto deve ter entre 3 e 200 caracteres.")
    @Column(name = "product_description")
    private String productDescription;

    @NotNull(message = "A unidade do produto é obrigatória.")
    @Size(min = 1, max = 200, message = "A unidade de medida deve ter entre 1 e 200 caracteres.")
    @Column(name = "unidade_medida")
    private String unidadeMedida;

    @NotNull(message = "O preço do produto é obrigatório.")
    @DecimalMin(value = "0.01", inclusive = true, message = "O preço deve ser maior que zero.")
    @Column(name = "preco")
    private BigDecimal preco;

    @NotNull(message = "A taxa do produto é obrigatória.")
    @DecimalMin(value = "0.00", inclusive = true, message = "A taxa deve ser maior ou igual a zero.")
    @Column(name = "tax_iva")
    private BigDecimal taxIva;

    @NotNull(message = "O preço final é obrigatório.")
    @DecimalMin(value = "0.00", inclusive = true, message = "O preço final deve ser maior ou igual a zero.")
    @Column(name = "final_price")
    private BigDecimal finalPrice;

    @NotNull(message = "Product Group Id é obrigatório.")
    @Column(name = "product_group_id")
    private Long productGroupId;

    @NotNull(message = "Product Type Id é obrigatório.")
    @Column(name = "product_type_id")
    private Long productTypeId;

    @Column(name = "empresa_id")
    @NotNull(message = "empresa é obrigatória")
    private Long empresaId;

    @Column(name = "produto_pai_id")
    private Long produtoPaiId;

    @NotNull(message = "Unidade de Medida Id é obrigatório.")
    @Column(name = "unidade_medida_id")
    private Long unidadeMedidaId;

    @Column(name = "status")
    private Boolean status = true;

    @Column(name = "imagem")
    private String imagem;

    @Size(max = 50, message = "O intervalo de referência deve ter no máximo 50 caracteres.")
    @Pattern(
            regexp = "^$|^\\d+(\\.\\d+)?-\\d+(\\.\\d+)?$",
            message = "O intervalo de referência deve estar no formato 'min-max' (ex.: 4.5-5.9) ou vazio."
    )
    @Column(name = "intervalo_referencia")
    private String intervaloReferencia;

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductGroup() {
        return productGroup;
    }

    public void setProductGroup(String productGroup) {
        this.productGroup = productGroup;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public String getUnidadeMedida() {
        return unidadeMedida;
    }

    public void setUnidadeMedida(String unidadeMedida) {
        this.unidadeMedida = unidadeMedida;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

    public BigDecimal getTaxIva() {
        return taxIva;
    }

    public void setTaxIva(BigDecimal taxIva) {
        this.taxIva = taxIva;
    }

    public BigDecimal getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(BigDecimal finalPrice) {
        this.finalPrice = finalPrice;
    }

    public Long getProductGroupId() {
        return productGroupId;
    }

    public void setProductGroupId(Long productGroupId) {
        this.productGroupId = productGroupId;
    }

    public Long getProductTypeId() {
        return productTypeId;
    }

    public void setProductTypeId(Long productTypeId) {
        this.productTypeId = productTypeId;
    }

    public Long getEmpresaId() {
        return empresaId;
    }

    public void setEmpresaId(Long empresaId) {
        this.empresaId = empresaId;
    }

    public Long getProdutoPaiId() {
        return produtoPaiId;
    }

    public void setProdutoPaiId(Long produtoPaiId) {
        this.produtoPaiId = produtoPaiId;
    }

    public Long getUnidadeMedidaId() {
        return unidadeMedidaId;
    }

    public void setUnidadeMedidaId(Long unidadeMedidaId) {
        this.unidadeMedidaId = unidadeMedidaId;
    }

    public Boolean isStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    public String getIntervaloReferencia() {
        return intervaloReferencia;
    }

    public void setIntervaloReferencia(String intervaloReferencia) {
        this.intervaloReferencia = intervaloReferencia;
    }
}
