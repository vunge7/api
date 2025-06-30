package com.dvml.api.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class Line {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "line_number", length = 60)
    private int lineNumber;
    @Column(name = "product_code", length = 60)
    private String productCode;
    @Column(name = "product_description", length = 200)
    private String productDescription;
    @Column(name = "quantity", length = 20)
    private double quantity;
    @Column(name = "unit_of_measure", length = 20)
    private String unitOfMeasure;
    @Column(name = "unit_price", precision = 30, scale = 2)
    private BigDecimal unitPrice;
    @Column(name = "tax_base", precision = 30, scale = 2)
    private BigDecimal taxBase;
    @Column(name = "tax_point_date")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date taxPointDate;
    @Column(name = "reference", length = 200)
    private String reference;
    @Column(name = "description", length = 200)
    private String description;
    @Column(name = "debit_amount", precision = 30, scale = 2)
    private BigDecimal debitAmount;
    @Column(name = "credit_amount", precision = 30, scale = 2)
    private BigDecimal creditAmount;
    @Column(name = "tax_type", length = 200)
    private String taxType;
    @Column(name = "tax_country_region", length = 200)
    private String taxCountryRegion;
    @Column(name = "tax_code", length = 200)
    private String taxCode;
    @Column(name = "tax_percentage", length = 20)
    private double taxPercentage;
    @Column(name = "tax_amount", precision = 30, scale = 2)
    private BigDecimal taxAmount;
    @Column(name = "tax_exception_reason", length = 60)
    private String taxExceptionReason;
    @Column(name = "tax_exception_code", length = 3)
    private String taxExceptionCode;
    @Column(name = "source_document_id", length = 30)
    private int sourceDocumentId;
    @Column(name = "line_discount", precision = 30, scale = 2)
    private BigDecimal lineDiscount;
    @Column(name = "line_total", precision = 30, scale = 2)
    private BigDecimal lineTotal;
}
