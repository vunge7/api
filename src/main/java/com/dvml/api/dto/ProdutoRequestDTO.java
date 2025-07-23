package com.dvml.api.dto;

public class ProdutoRequestDTO {
    private String productType;
    private long productTypeId;
    private String productCode;
    private String productGroup;
    private long productGroupId;
    private String productDescription;
    private String unidadeMedida;
    private long unidadeMedidaId;
    private String preco;
    private String taxIva;
    private String finalPrice;
    private String status;

    // Getters e setters
    public String getProductType() { return productType; }
    public void setProductType(String productType) { this.productType = productType; }

    public long getProductTypeId() { return productTypeId; }
    public void setProductTypeId(long productTypeId) { this.productTypeId = productTypeId; }

    public String getProductCode() { return productCode; }
    public void setProductCode(String productCode) { this.productCode = productCode; }

    public String getProductGroup() { return productGroup; }
    public void setProductGroup(String productGroup) { this.productGroup = productGroup; }

    public long getProductGroupId() { return productGroupId; }
    public void setProductGroupId(long productGroupId) { this.productGroupId = productGroupId; }

    public String getProductDescription() { return productDescription; }
    public void setProductDescription(String productDescription) { this.productDescription = productDescription; }

    public String getUnidadeMedida() { return unidadeMedida; }
    public void setUnidadeMedida(String unidadeMedida) { this.unidadeMedida = unidadeMedida; }

    public long getUnidadeMedidaId() { return unidadeMedidaId; }
    public void setUnidadeMedidaId(long unidadeMedidaId) { this.unidadeMedidaId = unidadeMedidaId; }

    public String getPreco() { return preco; }
    public void setPreco(String preco) { this.preco = preco; }

    public String getTaxIva() { return taxIva; }
    public void setTaxIva(String taxIva) { this.taxIva = taxIva; }

    public String getFinalPrice() { return finalPrice; }
    public void setFinalPrice(String finalPrice) { this.finalPrice = finalPrice; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
