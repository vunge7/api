package com.dvml.api.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.sound.sampled.Line;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor

public class SourceDocument {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "invoice_no", length = 60)
    private String invoiceNo;
    @Column(name = "invoice_status", length = 1)
    private String invoiceStatus;
    @Column(name = "invoice_status_date")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date invoiceStatusDate;
    @Column(name = "source_id", length = 30)
    private String sourceId;
    @Column(name = "source_billing", length = 1)
    private String sourceBilling;
    @Column(name = "hash", length = 172)
    private String hash;
    @Column(name = "hash_control", length = 70)
    private String hashControl;
    @Column(name = "invoice_date")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date invoiceDate;
    @Column(name = "invoice_type", length = 2)
    private String invoiceType;
    @Column(name = "self_billing_indicator", length = 250)
    private int selfBillingIndicator;
    @Column(name = "cash_vatscheme_indicator", length = 1000)
    private int cashVatschemeIndicator;
    @Column(name = "third_parties_billing_indicator", length = 1000)
    private int thirdPartiesBillingIndicator;

    @Column(name = "system_entry_date")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date systemEntryDate;

    @Column(name = "tax_payable", precision = 30, scale = 2)
    private BigDecimal taxPayable;
    @Column(name = "net_total", precision = 30, scale = 2)
    private BigDecimal netTotal;
    @Column(name = "gross_total", precision = 30, scale = 2)
    private BigDecimal grossTotal;
    @Column(name = "discount_total", precision = 30, scale = 2)
    private BigDecimal discountTotal;

    @Column(name = "customer_id", length = 30)
    private int customerId;


}
