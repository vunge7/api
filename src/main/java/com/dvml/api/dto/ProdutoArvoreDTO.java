package com.dvml.api.dto;

import java.util.List;

public class ProdutoArvoreDTO {

    private Long id;
    private String productCode;
    private String productDescription;
    private List<ProdutoArvoreDTO> filhos;

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public List<ProdutoArvoreDTO> getFilhos() {
        return filhos;
    }

    public void setFilhos(List<ProdutoArvoreDTO> filhos) {
        this.filhos = filhos;
    }
}