package com.dvml.api.dto;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@NoArgsConstructor

public class SourceDocumentDTO
{
    private long id;
    private String invoiceNo;
    private String invoiceStatus;
    private String sourceId;
    private String sourceBilling;
    private String invoiceType;
    private String cashVatschemeIndicator;
    private String thirdPartiesBillingIndicator;

}
